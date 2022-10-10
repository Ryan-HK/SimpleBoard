package org.zerock.myapp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyPageDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.ControllerException;
import org.zerock.myapp.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequestMapping("/replies")
@RestController

@Log4j2
@AllArgsConstructor
public class ReplyController {

	private ReplyService service;
	
	
	//-- 1. 댓글을 등록
	
	//-- consumes : 서버로 부터 들어오는 데이터타입을 지정
	//				즉 json타입만 처리하는 것을 지정하였다.
	//-- produces : 댓글의 처리결과가 정상적으로 처리되었는 지, 문자열로알려준다.
	//-- @RequestBody : 서버로 부터 Body에 들어오는 json타입을 ReplyVO로 변환
	@PostMapping(
			value = "/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<String> create(@RequestBody ReplyDTO dto) throws ControllerException{
		log.info("create({}) invoked.", dto);
		
		try {
			boolean result = this.service.register(dto);
			
			return result? new ResponseEntity<>("success", HttpStatus.OK)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch(Exception e) {
			throw new ControllerException(e);
		} // try-catch
			
	} // create
	
	
	//-- 2. 특정 게시물의 댓글 목록 획득
	@GetMapping(
			value="/pages/{bno}/{page}",
			produces = {
					MediaType.APPLICATION_JSON_VALUE
			}
				)
	public ResponseEntity<ReplyPageDTO> getList(
			@PathVariable("page") int page,
			@PathVariable("bno") int bno) throws ControllerException{
		log.info("getList({}, {}) invoked.", page, bno);
		
		try {
			BoardDTO dto = new BoardDTO();
			dto.setBno(bno);
			
			// 특정 게시물의 총 댓글 수
			int totalAmount = this.service.getTotal(dto);
	
			// 현재 페이지에대한 처리 객체
			Criteria cri = new Criteria();
			cri.setPagesPerPage(5);
			cri.setAmount(5);
			cri.setCurrPage(page);
			
			List<ReplyVO> list = this.service.getList(cri, dto);
			
			ReplyPageDTO replyPageDTO = new ReplyPageDTO(cri, totalAmount, list);
			
			return new ResponseEntity<>(replyPageDTO, HttpStatus.OK);
			
		} catch(Exception e) {
			throw new ControllerException(e);
		}
		
	} // getList
	
	
	//-- 3. 특정게시물의 댓글 수정
	@PostMapping(
			value = "/modify",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<String> modifyReply(
			@RequestBody ReplyDTO dto
			) throws ControllerException {
		
		log.info("modify({}, {}) invoked.", dto);
		
		try {

			
			boolean result = this.service.modify(dto);
			
			// 왜? ResponseEntity를 리턴값으로 지정한 이유?
			// --> HTTP전송 시, Body에 payload(데이타)를 담을뿐만 아니라, status(상태코드)까지 전송하기 위함이다.
			
			//-- 성공 시, ResponseEntity에 "success"라는 String을 payload에 담고, status에는 OK (200번 CODE)를 담아 리턴한다.
			return result? new ResponseEntity<>("success", HttpStatus.OK)
					//-- 실패 시, Body에는 아무것도 담지 않으며, INTERNAL_SERVER_ERROR (500번 CODE)를 전송한다.
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (Exception e) {
			throw new ControllerException(e);
		}

	} // modifyReply
	
	
	@DeleteMapping(
			value = "/articles/{bno}/replys/{rno}",
			produces = {MediaType.TEXT_PLAIN_VALUE}
			)
	public ResponseEntity<String> remove(
			@PathVariable Integer rno,
			@PathVariable Integer bno
			) throws ControllerException {
		log.info("remove({}) invoked.", rno);
		
		try {
			ReplyDTO dto = new ReplyDTO();
			dto.setRno(rno);
			dto.setBno(bno);
			
			boolean result = this.service.remove(dto);
			
			return result? new ResponseEntity<>("success", HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (Exception e) {
			throw new ControllerException(e);
		}

	} // removeReply
	
	@PostMapping(
			value = "/getTime",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Date> getCurrentTime() throws ControllerException {
		log.info("getCurrentTime() invoked.");
		
		try {
			Date result = this.service.getCurrentTime();
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ControllerException(e);
		}
	
	}
	
} // end class
