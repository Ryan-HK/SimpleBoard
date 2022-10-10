package org.zerock.myapp.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.exception.DAOException;
import org.zerock.myapp.exception.ServiceException;
import org.zerock.myapp.mapper.FileUploadMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@Service
public class FileUploadServiceImpl implements FileUploadService{
	
	private final FileUploadMapper mapper;

	//-- 1. File Upload
	@Override
	public void fileUploadRegister(List<MultipartFile> uploadFiles, int bno) throws ServiceException {
		log.trace("fileUpload() invoked.");
		
		
		//-- Step1 : 파일업로드를 위한 폴더 생성
		String uploadBaseDir = "C:/Temp/upload/";		// 파일업로드를 위한 Base경로
		
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String todayDir = sdf.format(today);
		
		String folderDir = uploadBaseDir + todayDir;	// 파일업로드를 위한 경로
		
		File uploadFolder = new File(folderDir);
		
		
		if(!uploadFolder.exists()) {
			uploadFolder.mkdir();
		}
		
		for(MultipartFile multipartFile : uploadFiles) {
			
			BoardAttachDTO dto = new BoardAttachDTO();
			dto.setBno(bno);
			log.info("\t+ bno : {}", bno);
			
			//Step2 - (1) : 원본파일명 얻기
			String fileName = multipartFile.getOriginalFilename();
			log.info("\t+ fileName : {}", fileName);
			
			dto.setFileName(fileName);
			//Step2 - (2) : 파일업로드를 위한 임시파일명 생성
			String uuid = UUID.randomUUID().toString();
			log.info("\t+ uuid : {}", uuid);
			
			dto.setUuid(uuid);
			
			//Step3 - (3) : 파일이 저장되는 폴더 저장
			dto.setUploadPath(folderDir);
			
			//Step4 - (4) : 파일 업로드
			File saveFile = new File(folderDir, uuid);
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				throw new ServiceException(e);
			} // try-catch
			
			
			try {
				mapper.fileInsert(dto);
			} catch (DAOException e) {
				throw new ServiceException(e);
			} // try-catch
			
		} // enhanced-for

	} // fileUpload

} // end class
