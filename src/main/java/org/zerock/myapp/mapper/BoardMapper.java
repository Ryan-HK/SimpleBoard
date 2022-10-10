package org.zerock.myapp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.BoardWithAttachVO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.exception.DAOException;

public interface BoardMapper {
	
	// 1. 게시판 테이블의 전체 목록 조회하기
	@Select(value="SELECT /*+ index_desc(tbl_board) */ * FROM tbl_board where bno > 0")
	public abstract List<BoardVO> selectAllList() throws DAOException;
	
	// 2. 새로운 게시글을 등록하기 - Mapper XML 파일로 처리
	public abstract Integer insert(BoardDTO dto) throws DAOException;
	public abstract Integer insertSelectKey(BoardDTO dto) throws DAOException;
	
	// 3. 기존 게시글 상세 조회하기 - Mapper XML파일로 처리
	public abstract BoardVO select(BoardDTO dto) throws DAOException;
	
	// 4. 기존 게시글 수정하기 - Mapper XML파일로 처리
	public abstract Integer update(BoardDTO dto) throws DAOException;
	
	// 5. 기존 게시글 삭제하기
	@Delete(value="DELETE FROM tbl_board WHERE bno = #{bno}")
	public abstract Integer delete(@Param("bno") Integer bno) throws DAOException;
	
	// 6. 페이징 처리가 적용된 게시물 목록 조회하기
	public abstract List<BoardVO> selectListWithPaging(Criteria cri) throws DAOException;
	
	// 7. 총 게시물의 개수 반환
	public abstract Integer getTotalCount() throws DAOException;
	
	// 8. 현재 시각 구하기 (게시판)
	@Select(value="SELECT current_date FROM dual")
	public abstract Date getCurrentTime() throws DAOException;
	
	// 9. 현재 게시물의 총 댓글개수 반환 (페이징처리)
	public abstract Integer getTotalCountReply(BoardDTO dto) throws DAOException;
	
	
	
} // end interface
