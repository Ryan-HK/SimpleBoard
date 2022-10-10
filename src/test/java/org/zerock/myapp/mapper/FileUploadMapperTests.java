package org.zerock.myapp.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.myapp.domain.BoardAttachDTO;
import org.zerock.myapp.exception.DAOException;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileUploadMapperTests {
	
	@Setter(onMethod_= {@Autowired})
	private FileUploadMapper mapper;
	

	
	@Disabled
	@Test
	@Order(1)
	@DisplayName("testFileDelete")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testFileDelete() throws DAOException {
		log.trace("testFileDelete() invoked.");
		
		List<Integer> list = new ArrayList<>();
		
		list.add(56);
		list.add(55);
		
		this.mapper.fileDelete(list);
		
	} // testGetTotalCountReply
	
	
	
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("testSelectDeleteAttach")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testSelectDeleteAttach() throws DAOException {
		log.trace("testSelectDeleteAttach() invoked.");
		
		List<Integer> list = new ArrayList<>();
		
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		
		List<BoardAttachDTO> dtoList = this.mapper.getDeleteAttachDTO(list);
		
		log.info("\t+ dtoList : {}", dtoList);
		
		
	} // testGetTotalCountReply
	
	
	
	

} // end class
