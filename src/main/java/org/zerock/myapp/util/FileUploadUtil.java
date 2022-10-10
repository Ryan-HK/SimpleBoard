package org.zerock.myapp.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

public class FileUploadUtil {
	
	//-- 1. File Upload
	public static List<BoardAttachDTO> fileUploadRegister(List<MultipartFile> uploadFiles) {
		log.trace("fileUpload() invoked.");
		
		List<BoardAttachDTO> list = new ArrayList<>();
		
		//-- Step1 : 파일업로드를 위한 폴더 생성

	
		String folderDir = makeFolder() ;	// 파일업로드를 위한 경로 생성
		

		//-- STep2 : 폴더를 생성한 위치에 파일 생성
		for(MultipartFile multipartFile : uploadFiles) {
			
			BoardAttachDTO dto = saveFile(multipartFile, folderDir);

			//리스트객체에 BoardAttachDTO 저장
			list.add(dto);
			
		} // enhanced-for
		return list;

	} // fileUpload
	
	
	
	
	//-- 2. 파일이 저장되는 폴더 생성
	public static String makeFolder() {
		log.trace("makeFolder() invoked.");
		
		String uploadBaseDir = "C:/Temp/upload/";		// 파일업로드를 위한 Base경로
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String todayDir = sdf.format(today);
		
		String folderDir = uploadBaseDir + todayDir;	// 파일업로드를 위한 경로
		
		File uploadFolder = new File(folderDir);
		
		
		if(!uploadFolder.exists()) {
			uploadFolder.mkdir();
		}
		
		// 파일업로드 경로 (String)값 리턴
		return folderDir;
		
	} // makeFolder
	
	
	//-- 3. 파일저장 (단위파일)
	public static BoardAttachDTO saveFile(MultipartFile file, String folderDir) {
		log.trace("saveFile() invoked.");
		
		BoardAttachDTO dto = new BoardAttachDTO();
		
		//Step2 - (1) : 원본파일명 얻기
		String fileName = file.getOriginalFilename();
		log.info("\t+ fileName : {}", fileName);
		
		String fileExtension = FilenameUtils.getExtension(fileName);
		log.info("\t+ fileExtension : {}", fileExtension);
		
		dto.setFileName(fileName);
		//Step2 - (2) : 파일업로드를 위한 임시파일명 생성 (random UUID 사용)
		String uuid = UUID.randomUUID().toString()+"."+fileExtension;
		log.info("\t+ uuid : {}", uuid);
		
		dto.setUuid(uuid);
		
		//Step3 - (3) : 파일이 저장되는 폴더 저장 (물리적 경로)
		dto.setUploadPath(folderDir);
		
		//Step4 - (4) : 파일 업로드
		File saveFile = new File(folderDir, uuid);
		
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} // try-catch
		
		return dto;
		
	} // saveFile
	
	
	//-- 4. 물리적 경로의 파일 삭제
	public static void deleteFile(List<BoardAttachDTO> AttachList) {
		
		AttachList.forEach(attachDTO -> {
			String uploadPath = attachDTO.getUploadPath();
			String uuid = attachDTO.getUuid();
			
			String targetDir = uploadPath + "/" + uuid;
			
			File deleteFile = new File(targetDir);
			deleteFile.delete();
		});
		
	} // deleteFile


} // end class
