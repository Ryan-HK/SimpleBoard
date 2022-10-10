package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;

import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.ServiceException;

public interface ReplyService {

	//-- 1. 새로운 댓글을 등록
	public abstract boolean register(ReplyDTO dto) throws ServiceException;
	
	//-- 2. 특정 댓글을 획득
	public abstract ReplyVO get(ReplyDTO dto) throws ServiceException;
	
	//-- 3. 특정 댓글을 수정
	public abstract boolean modify(ReplyDTO dto) throws ServiceException;
	
	//-- 4. 특정 댓글을 삭제
	public abstract boolean remove(ReplyDTO dto) throws ServiceException;
	
	//-- 5. 특정게시물의 페이징처리된 댓글목록 얻어오기
	public abstract List<ReplyVO> getList(Criteria cri, BoardDTO dto) throws ServiceException;
	
	//-- 6. 특정게시물의 총 댓글개수 구하기
	public abstract int getTotal(BoardDTO dto) throws ServiceException;
	
	//-- 7. 실시간 날짜표기 방식 
	public abstract Date getCurrentTime() throws ServiceException;
	
} // end interface
