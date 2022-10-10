package org.zerock.myapp.mapper;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.DAOException;

public interface ReplyMapper {

	//-- 1. 댓글을 등록하기
	public abstract Integer insert(ReplyDTO dto) throws DAOException;
	
	//-- 2. 특정 댓글 읽기
	public abstract ReplyVO read(ReplyDTO dto) throws DAOException; 
	
	//-- 3. 특정 댓글 삭제
	@Delete(value = "DELETE FROM tbl_reply WHERE rno = #{rno}")
	public abstract Integer delete(@Param("rno") Integer rno) throws DAOException;
	
	//-- 4. 특정 댓글 수정
	public abstract Integer update(ReplyDTO dto) throws DAOException;
	
	//-- 5. 페이징 처리된 댓글목록구하기
	public abstract List<ReplyVO> getListWithPaging(
						@Param("cri") Criteria cri,
						@Param("bno") Integer bno
			) throws DAOException;
	
	//-- 6. 특정 게시물의 총 댓글 개수 구하기
	public abstract int getTotalCount(@Param("bno") Integer bno) throws DAOException;
	
	//-- 7. 현재 시각 구하기 (댓글)
	@Select(value="SELECT current_date FROM dual")
	public abstract Date getCurrentTime() throws DAOException;
	
	//-- 8. 댓글 등록 및 삭제 시, tbl_board의 replyCnt 개수 올리기
	public abstract void updateReplyCnt(@Param("bno") Integer bno, @Param("amount") int amount);
	
} // end interface
