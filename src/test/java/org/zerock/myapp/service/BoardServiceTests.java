package org.zerock.myapp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
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
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.BoardVO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.exception.ServiceException;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

@Log4j2

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/**/*-context.xml",
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardServiceTests {

//	@Setter(onMethod_= {@Autowired})
	private final BoardService service;
	
	@Autowired
	public BoardServiceTests(BoardService service) {
		this.service = service;
	}
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		assertNotNull(this.service);
		
		log.info("\t+ this.service : {}", this.service);
	} // beforeAll
	
	
	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. BoardService.getList()")
	void testGetList() throws ServiceException {
		log.trace("testGetList() invoked.");
	
		List<BoardVO> list = new ArrayList<>();
		
		list = service.getList();
		
		list.forEach(log::info);
		
	} // testGetList
	
	
	@Disabled
	@Test
	@Order(2)
	@DisplayName("2. BoardService.register()")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testRegister() throws ServiceException {
		log.trace("testRegister() invoked.");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setTitle("TITLE_NEW");
		dto.setContent("CONTENT_NEW");
		dto.setWriter("WRITER_NEW");
		
		log.info("\t+ result : {}", this.service.register(dto));
		
	} // testRegister
	
	@Disabled
	@Test
	@Order(3)
	@DisplayName("3. BoardService.modify()")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testModify() throws ServiceException {
		log.trace("testModify() invoked.");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(30);
		dto.setTitle("UPDATE_NEW");
		dto.setContent("UPDATE_NEW");
		dto.setWriter("UPDATE_NEW");
		
		log.info("\t+ result : {}", this.service.modify(dto));
		
	} // testModify
	
	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. BoardService.remove()")
	void testRemove() throws ServiceException {
		log.trace("testRemove() invoked.");
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(132);
		
		log.info("\t+ result : {}", this.service.remove(dto));
		
	} // testRemove
	
	
	@Disabled
	@Test
	@Order(5)
	@DisplayName("5. BoardService.get()")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGet() throws ServiceException {
		log.trace("testGet() invoked.");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(50);
		
		log.info("\t+ result : {}", this.service.get(dto));
		
	} // testGet
	
	
//	@Disabled
	@Test
	@Order(6)
	@DisplayName("6. BoardService.getListPerPages()")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGetListPerPages() throws ServiceException {
		log.trace("testGetListPerPages() invoked.");
		
		Criteria cri = new Criteria();
		
		cri.setCurrPage(1);
		cri.setAmount(10);
		
		@Cleanup("clear")
		List<BoardVO> list = service.getListPerPage(cri);
		
		list.forEach(log::info);
		
	} // testGetListPerPages
	
	
//	@Disabled
	@Test
	@Order(7)
	@DisplayName("7. BoardService.getTotal")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGetTotal() throws ServiceException {
		log.trace("testGetTotal() invoked.");
		
		
		log.info("\t+ total : {}", this.service.getTotal());
		
	} // testGetTotal
	
	
} // end class
