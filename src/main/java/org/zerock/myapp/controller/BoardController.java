                 package org.zerock.myapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.PageDTO;
import org.zerock.myapp.exception.ControllerException;
import org.zerock.myapp.service.BoardService;
import org.zerock.myapp.util.FileUploadUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2

@RequestMapping("/board")
@Controller
public class BoardController {
	
	
	//-- 생성자를 이용한 BoardService 주입 
	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		log.trace("Constructor({}) invoked.");
		
		this.boardService = boardService;
	} // Constructor

	
	
	//---------------------------------
	//-- 1. 전체 게시물을 조회
	//---------------------------------
//	@GetMapping("/list")
	public void list(Model model) throws ControllerException {
		log.trace("list() invoked.");
		
		try {
			List<BoardVO> list = new ArrayList<>();
			
			list = boardService.getList();
			list.forEach(log::info);
			
			
			model.addAttribute("board", list);
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch	
		
	} // list
	
	//---------------------------------
	//-- 2. 게시물을 등록
	//---------------------------------
	@PostMapping("/register")
	public String register(
			BoardDTO dto, 
			@RequestParam("files")List<MultipartFile> multipartfiles, 
			RedirectAttributes rttrs) throws ControllerException {
		log.trace("register({}) invoked.", multipartfiles);
		
		try {
			
			// multipartfiles 존재 시, 
			if(!multipartfiles.get(0).isEmpty()) {
				List<BoardAttachDTO> attachDTO = FileUploadUtil.fileUploadRegister(multipartfiles);
				
				dto.setAttachList(attachDTO);
			}
			
			boolean result = this.boardService.register(dto);
			log.info("\t+ selectKey : {}", result);
			
			rttrs.addAttribute("result", result ? "SUCCESS(" + dto.getBno() + ")" : "FAILURE");
			
			return "redirect:/board/list";
			
		} catch (Exception e) {
			throw new ControllerException(e);
		}
		
	} // register
	
	
	//---------------------------------
	//-- 3. 게시물을 수정
	//---------------------------------
	@PostMapping("/modify")
	public String modify(
			BoardDTO dto,
			Criteria cri,
			RedirectAttributes rttrs,
			@RequestParam(value="files", required=false)List<MultipartFile> files,
			@RequestParam(value="attach_no", required=false)List<Integer> listAttachNo,
			@RequestParam(value="deleteFile", required=false)List<Integer> deleteFile
			)
		throws ControllerException {
		log.trace("modify() invoked.");
		
		log.info("\t+ files : {}", files);
		log.info("\t+ listAttachNo : {}", listAttachNo);
		log.info("\t+ deleteFile : {}", deleteFile);
		
		
		try {
			
			List<BoardAttachDTO> list = new ArrayList<>();

			String folderDir = FileUploadUtil.makeFolder();

			// 수정한 이미지 반영하기
			if(files != null) {
				for(int i=0; i<files.size(); i++) {
					
					if(files.get(i).getSize() > 0) {
						BoardAttachDTO attach = FileUploadUtil.saveFile(files.get(i), folderDir);
						attach.setAttach_no(listAttachNo.get(i));
						
						list.add(attach);
					}
				} // for
			}

			
			log.info(list);
			dto.setAttachList(list);
			
			// 삭제할 이미지 삭제 진행
			if(deleteFile != null) {
				dto.setDeleteFile(deleteFile);
			}
			
			
			boolean isModify = boardService.modify(dto);
						
			rttrs.addFlashAttribute("result", isModify ? "SUCCESS(" + dto.getBno() + ")" : "FAILURE");
			rttrs.addAttribute("bno", dto.getBno());
			rttrs.addAttribute("currPage", cri.getCurrPage());
			log.info("\t+ cri.getCurrPage : {}",cri.getCurrPage());
			
			return "redirect:/board/get";
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
		
	} // modify
	
	
	//---------------------------------
	//-- 4. 게시물을 삭제
	//---------------------------------
	@PostMapping("/remove")
	public String remove(
			BoardDTO dto,
			Criteria cri,
			RedirectAttributes rttrs)
		throws ControllerException {
		log.trace("remove() invoked.");
		
		try {
			boolean isRemove = boardService.remove(dto);
			
			rttrs.addAttribute("result", isRemove ? "SUCCESS(" + dto.getBno() + ")" : "FAILURE");
			rttrs.addAttribute("currPage", cri.getCurrPage());
			
			return "redirect:/board/list";
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
		
	} // remove
	
	
	//---------------------------------
	//-- 5. 게시물 1개를 상세조회
	//---------------------------------
	@GetMapping("/get")
	public void get(
			BoardDTO dto,
			@ModelAttribute("cri")Criteria cri,
//			@RequestParam("currPage")String currPage,
			Model model) throws ControllerException {
		log.trace("get() invoked.");
		
		try {
			BoardVO vo = boardService.get(dto);
			log.info("\t+ vo : {}", vo);	
			
			// 게시물에 대한 총 댓글 개수 구하기 : 댓글 마지막 페이지 바인딩
			
			Integer totalAmount = this.boardService.getTotalReply(dto);
			int replyRealEndPage = (int) Math.ceil((totalAmount * 1.0)/cri.getAmount());
	
			
			
			model.addAttribute("board", vo);
			model.addAttribute("replyRealEndPage", replyRealEndPage);
			
			log.info("\t+ currPage : {}", cri.getCurrPage());
			
		} catch (Exception e) {
			throw new ControllerException(e);
		} // try-catch
		
	} // get
	
	
	//---------------------------------
	//-- 6. 게시물 등록화면으로 이동
	//---------------------------------
	
	@GetMapping("/register")
	public void register() {
		
	} // register
	
	
	//---------------------------------
	//-- 8. 전체게시물 조회 (페이징 처리)
	//---------------------------------
	@GetMapping("/list")
	public void listPerPage(Criteria cri, Model model) throws ControllerException {
		log.trace("listPerPage({}) invoked.", cri);
		
		try {
			
			List<BoardVO> list = boardService.getListPerPage(cri);
			
			PageDTO pageDTO = new PageDTO(cri, this.boardService.getTotal());
			
			Date currTime = boardService.getCurrentTime();		// 현재 시각 구하기
			log.info("currTime : {}", currTime);
			
			model.addAttribute("board", list);
			model.addAttribute("pageMaker", pageDTO);
			model.addAttribute("currTime", currTime);
			
		} catch (Exception e) {
			throw new ControllerException(e);
		}
		
	} // listPerPage
	

} // end class
