package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.BoardWithAttachVO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.exception.ServiceException;

public interface BoardService {
	
	// -- 1. 게시글 전체목록 획득
	public abstract List<BoardVO> getList() throws ServiceException;
	
	// -- 2. 새로운 게시글 등록
	public abstract boolean register(BoardDTO dto) throws ServiceException;
	
	// -- 3. 기존 게시글 수정
	public abstract boolean modify(BoardDTO dto) throws ServiceException;
	
	// -- 4. 기존 게시글 삭제
	public abstract boolean remove(BoardDTO dto) throws ServiceException;
	
	// -- 5. 기존 게시글 상세조회
	public abstract BoardVO get(BoardDTO dto) throws ServiceException;
	
	// -- 6. 페이징 처리된 게시글 목록 획득
	public abstract List<BoardVO> getListPerPage(Criteria cri) throws ServiceException;
	
	// -- 7. 총 게시물 개수 구하기
	public abstract int getTotal() throws ServiceException;

	// -- 8. 실시간 날짜표기 방식 
	public abstract Date getCurrentTime() throws ServiceException;
	
	// -- 9. 상세 조회 시, 게시물에 등록된 댓글 개수 불러오기
	public abstract Integer getTotalReply(BoardDTO dto) throws ServiceException;

	
} // interface
