package org.zerock.myapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2

@Service
public class ReplyServiceImpl implements ReplyService {

	
	private final ReplyMapper mapper;
	
	@Autowired
	public ReplyServiceImpl(ReplyMapper mapper) {
		this.mapper = mapper;
	} // Constructor - Inject

	
	//--------------------------------------
	//-- 1. 댓글을 등록
	//--------------------------------------
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean register(ReplyDTO dto) throws ServiceException {
		log.trace("register({}) invoked.", dto);
		
		try {
			
			this.mapper.updateReplyCnt(dto.getBno(), +1);
			
			return this.mapper.insert(dto) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // register

	
	//--------------------------------------
	//-- 2. 특정 댓글 1개를 획득
	//--------------------------------------
	@Override
	public ReplyVO get(ReplyDTO dto) throws ServiceException {
		log.trace("get({}) invoked.", dto);
		
		try {
			
			
			
			return this.mapper.read(dto);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // get
	
	
	//--------------------------------------
	//-- 3. 특정 댓글을 수정
	//--------------------------------------
	@Override
	public boolean modify(ReplyDTO dto) throws ServiceException {
		log.trace("modify({}) invoked.", dto);
	
		try {
			
			return this.mapper.update(dto) == 1;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch
		
	} // modify

	
	//--------------------------------------
	//-- 4. 특정 댓글을 삭제
	//--------------------------------------
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean remove(ReplyDTO dto) throws ServiceException{
		log.trace("remove({}) invoked.", dto);
		
	
		try {
			this.mapper.updateReplyCnt(dto.getBno(), -1);
			
			return this.mapper.delete(dto.getRno()) == 1;
		} catch (UncategorizedSQLException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
	} // remove

	
	//--------------------------------------
	//-- 5. 특정 게시물의 페이징처리된 댓글목록 얻어오기
	//--------------------------------------
	@Override
	public List<ReplyVO> getList(Criteria cri, BoardDTO dto) throws ServiceException {
		log.trace("getList({},{}) invoked.", cri, dto);
		
		try {
			// 특정 게시물 번호
			Integer targetBno = dto.getBno();
			log.info("\t+ targetBno : {}", targetBno);
			
			// 페이징 처리 된, 댓글 목록
			return mapper.getListWithPaging(cri, targetBno);
			
		} catch (Exception e) {
			throw new ServiceException(e);
		} // try-catch

	} // getList

	
	//--------------------------------------
	//-- 6. 특정 게시물의 댓글개수 구하기
	//--------------------------------------
	@Override
	public int getTotal(BoardDTO dto) throws ServiceException {
		log.trace("getTotal({}) invoked.", dto);
		
		try {
			
			return mapper.getTotalCount(dto.getBno());
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} // get Total

	//--------------------------------------
	//-- 7. DB서버 시간 확인용 (시간 포맷팅)
	//--------------------------------------
	@Override
	public Date getCurrentTime() throws ServiceException {
	
		try {
			
			return mapper.getCurrentTime();
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	} // getCurrentTime
	
	



} // end class
