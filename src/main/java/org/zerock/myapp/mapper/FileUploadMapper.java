package org.zerock.myapp.mapper;

import java.util.List;

import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.domain.BoardAttachVO;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.exception.DAOException;

public interface FileUploadMapper {

	//1. 파일 업로드 내역 등록
	public Integer fileInsert(BoardAttachDTO dto) throws DAOException;
	
	//2. 파일 업로드 삭제
	public Integer fileDelete(List<Integer> list) throws DAOException;
	
	//3. 파일 업로드 수정
	public Integer fileModify(BoardAttachDTO dto) throws DAOException;
	
	//4. 물리적 삭제 파일정보 얻어오기 (attach_no)
	public List<BoardAttachDTO> getDeleteAttachDTO(List<Integer> attachNoList) throws DAOException;
	
	//5. 물리적 삭제 파일정보 얻어오기 (bno)
	public List<BoardAttachDTO> getDeleteAttachDTObyBno(BoardDTO dto) throws DAOException;
	
	//6. 어제날짜 파일정보 얻기
	public List<BoardAttachVO> getYesterdayFileList() throws DAOException;
	
} // end interface
