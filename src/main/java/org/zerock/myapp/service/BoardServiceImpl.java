package org.zerock.myapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.mapper.BoardMapper;
import org.zerock.myapp.mapper.FileUploadMapper;
import org.zerock.myapp.util.FileUploadUtil;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class BoardServiceImpl implements BoardService {

	
	@Setter(onMethod_= {@Autowired})
	private BoardMapper boardMapper;
	
	@Setter(onMethod_= {@Autowired})
	private FileUploadMapper fileUploadMapper;
	

	
	//------------------------------------------------
	//1. 게시판 전체글 리스트 조회
	//------------------------------------------------
	@Override
	public List<BoardVO> getList() throws ServiceException {
		log.trace("getList() invoked.");
		
		try {
			return this.boardMapper.selectAllList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	} // getList

	//------------------------------------------------
	// 2. 게시물 등록
	//------------------------------------------------
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean register(BoardDTO dto) throws ServiceException{
		
		try {

			this.boardMapper.insertSelectKey(dto);
			
			int selectKey = dto.getBno();
			
			log.info("\t+ selectKey : {}", selectKey);
			log.info("\t+ dto.getAttchList : {}", dto.getAttachList());
			
			if(dto.getAttachList() == null || dto.getAttachList().size() <= 0) {
				log.trace("getAttachList null 입니다.");
				
				return selectKey > 0 ? true : false;
				
			} // if
			

			for(BoardAttachDTO attach : dto.getAttachList()) {
				attach.setBno(selectKey);
				

				fileUploadMapper.fileInsert(attach);
		
				
			} // enhanced for
			
			return selectKey > 0 ? true : false;

		} catch (UncategorizedSQLException e) {
			throw e;
		} catch (Exception e) {
			log.info("예외가 발생하였습니다.");
			throw new ServiceException(e);
		}

	} // register

	//------------------------------------------------
	// 3. 게시물 수정
	//------------------------------------------------
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean modify(BoardDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.");
		
		List<BoardAttachDTO> attachList = dto.getAttachList();		// 수정이 진행 될, attach List
		List<Integer> deleteFile = dto.getDeleteFile();				// 파일 삭제가 이루어 질 attach_no List
		List<BoardAttachDTO> deleteList = new ArrayList<>();		// 물리적 파일 삭제List
		
		log.info("\t+ attachList : {}" , attachList);
		log.info("\t+ deleteFile : {}", deleteFile);
		log.info("\t+ deleteList : {}", deleteList);
		try {
			
			// 이미지 파일 삭제 (유효성검사)
			if(deleteFile != null && deleteFile.size() != 0) {
				log.info("\t+ 이미지 파일 삭제합니다.");
				// 이미지 삭제(물리적 삭제) Attach 정보 얻기
				this.fileUploadMapper.getDeleteAttachDTO(deleteFile).forEach(e -> {
					deleteList.add(e);
				});
				
				// DB서버 tbl_attach 테이블에 저장 된 항목 삭제
				this.fileUploadMapper.fileDelete(deleteFile);
			}
			
			
			// 이미지 파일 수정 (유효성검사)
			if(attachList != null && attachList.size() != 0) {
				log.info("\t+ 이미지 파일을 수정합니다.");
				// 이미지가 수정되어, 이미지 삭제(물리적삭제) Attach 정보 얻기
				List<Integer> list = attachList.stream()
					.map(BoardAttachDTO::getAttach_no).collect(Collectors.toList());
				
				this.fileUploadMapper.getDeleteAttachDTO(list).forEach(e -> {
					deleteList.add(e);
				});		
				for(BoardAttachDTO attach : attachList) {
					
					// DB 이미지 수정 진행
					this.fileUploadMapper.fileModify(attach);

				} // for
				
			} // if
			
			
			boolean result = this.boardMapper.update(dto) == 1;

			// 비지니스 로직이 끝난 후, 물리적 파일 삭제 실행
			FileUploadUtil.deleteFile(deleteList);	
			
			return result;
				
		} catch (Exception e) {
			throw new ServiceException(e);
		}
			
	} // modify

	//------------------------------------------------
	// 4. 게시물 삭제
	//------------------------------------------------
	@Override
	public boolean remove(BoardDTO dto) throws ServiceException {
		
		try {
			// 게시물에 저장 된 이미지 파일정보 얻어오기
			List<BoardAttachDTO> list = this.fileUploadMapper.getDeleteAttachDTObyBno(dto);
			
			// 게시물 삭제 진행
			boolean result = this.boardMapper.delete(dto.getBno()) == 1;	
			
			// 게시물에 저장 된 이미지 파일 삭제
			log.info("\t+ list : {}", list);
			FileUploadUtil.deleteFile(list);
			
			return result;	
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	} // remove

	
	//------------------------------------------------
	// 5. 1개에 대한 게시물 정보
	//------------------------------------------------
	@Override
	public BoardVO get(BoardDTO dto) throws ServiceException {

		try {
			return this.boardMapper.select(dto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	} // get

	
	//------------------------------------------------
	// 6. 게시물 리스트 얻기 (페이징 처리)
	//------------------------------------------------
	@Override
	public List<BoardVO> getListPerPage(Criteria cri) throws ServiceException {
		log.trace("getListPerPages({}) invoked.", cri);
		
		try {
			
			return this.boardMapper.selectListWithPaging(cri);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} // getListPerPages

	//------------------------------------------------
	// 7. 게시물의 총 개수 구하기
	//------------------------------------------------
	@Override
	public int getTotal() throws ServiceException {
		log.trace("getTotal() invoked.");
		
		try {
			
			return this.boardMapper.getTotalCount();
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} // getTotal

	
	//------------------------------------------------
	// 8. 현재 Oracle DB서버 시간 확인
	//------------------------------------------------
	@Override
	public Date getCurrentTime() throws ServiceException {
		
		try {

			return this.boardMapper.getCurrentTime();

		} catch (Exception e) {
			throw new ServiceException(e);
		}

	} // getTotal

	
	//------------------------------------------------
	// 9. 게시물의 총 리플 개수 구하기
	//------------------------------------------------
	@Override
	public Integer getTotalReply(BoardDTO dto) throws ServiceException {
		
		try {
			
			return this.boardMapper.getTotalCountReply(dto);
			
		} catch(Exception e) {
			throw new ServiceException(e);
		}
	} // getTotal
	

} // end class
