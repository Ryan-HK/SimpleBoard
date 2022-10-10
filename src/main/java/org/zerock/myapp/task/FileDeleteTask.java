package org.zerock.myapp.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.myapp.domain.BoardAttachVO;
import org.zerock.myapp.mapper.FileUploadMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor

@Component
public class FileDeleteTask {
	
	private final static String BASE_DIR = "C:/Temp/upload";

	private FileUploadMapper fileUploadMapper;
	
	//1. 어제 날짜 구하기
	public String getYesterDay() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String yesterDay = sdf.format(cal.getTime());
		
		return yesterDay;
		
	} // getYesterDay
	
	//2. Scheduled 실행
	@Scheduled(cron = "0 * * * * * ")
	public void taskDeleteOldFiles() throws Exception{
		log.trace("taskDeleteOldFiles() invoked.");
		
		//Step.1 : 어제날짜에 DB에 저장 된, 모든 파일 정보 얻기
		List<BoardAttachVO> yesterDayFileList = this.fileUploadMapper.getYesterdayFileList();
		log.info("\t+ yesterDayFileList : {}", yesterDayFileList);
		log.info("yesterDayFileList.size() : {}", yesterDayFileList.size());
		
		//Step.2 : Step.1로부터 얻은 파일정보에 대한 "모든 URI경로(Path)" 얻기 
		// -> 모든 URI경로를 List객체에 수집 (collect)
		// : yesterDay에 저장한 파일이 있을 경우에만 해당 로직 실행
		if(yesterDayFileList.size() != 0) {
			List<Path> yesterDayFilePathList = yesterDayFileList.stream().map( vo -> 
				Paths.get(vo.getUploadPath(), "/", vo.getUuid())
			).collect(Collectors.toList());
			
			log.info("\t+ yesterDayFilePathList : {}", yesterDayFilePathList);
			
			//Step.3 : 어제날짜에 저장된 폴더 경로 얻기
			String yesterDay = getYesterDay();
			File yesterDayDir = Paths.get("C:/Temp/upload", "/", yesterDay).toFile();
			log.info("\t+ yesterDayDir : {}", yesterDayDir);
			
			//Step.4 : Step.3경로에 있는 파일로부터, Step2에서 얻은 List객체에 포함여부를 확인하여, 
			//		   포함하지 않는 경우에만, File[]배열에 저장
			File[] removeFiles = yesterDayDir
					.listFiles( file -> yesterDayFilePathList.contains(file.toPath()) == false);
			
			
			//Step.5 : Step.4에서 얻은 File을 삭제진행
			for(File file : removeFiles) {
				log.info("\t+ DELETE File : {}", file);
				file.delete();
			}	
						
		}

		

		
	} // taskDeleteOldFiles
	
	
} // end class
