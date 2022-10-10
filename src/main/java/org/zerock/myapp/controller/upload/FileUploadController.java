package org.zerock.myapp.controller.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.myapp.exception.ControllerException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/upload")
@Controller
public class FileUploadController {
	
	
	
	@GetMapping("/getFile")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String filePath) throws ControllerException {
		log.trace("getFile({}) invoked.", filePath);
		
		//Step.1 : 파일 생성
		File file = new File(filePath);
		log.info("\t+ file : {}", file);
		

		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			throw new ControllerException(e);
		} // try-catch
		
		return result;
		
	} // getFile
	
} // end class
