package org.zerock.myapp.service;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.myapp.domain.BoardDTO;
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.ServiceException;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/**/*-context.xml",
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReplyServiceTests {

	@Setter(onMethod_= {@Autowired})
	private ReplyService service;
	
	@Disabled
	@Test
	@Order(1)
	void testRegister() throws ServiceException {
		log.trace("testRegister() invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		
		dto.setBno(1);
		dto.setReply("안녕하세요");
		dto.setReplyer("Ryan");
		
		log.info("\t+ result : {}", this.service.register(dto));
		
	} // testRegister
	
	@Disabled
	@Test
	@Order(2)
	void testGet() throws ServiceException {
		log.trace("testGet() invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		
		dto.setRno(20);
		
		log.info("\t+ result : {}", this.service.get(dto));
		
	} // testRegister
	
	
	@Disabled
	@Test
	@Order(3)
	void testModify() throws ServiceException {
		log.trace("testModify invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		
		dto.setRno(20);
		dto.setReply("수정합니다");
		
		log.info("\t+ result : {}", this.service.modify(dto));
		
	} // testModify
	
	@Disabled
	@Test
	@Order(4)
	void testRemove() throws ServiceException {
		log.trace("testRemove invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		
		dto.setRno(20);
		
		
		log.info("\t+ result : {}", this.service.remove(dto));
		
	} // testRemove
	
	
	@Disabled
	@Test
	@Order(5)
	void testGetList() throws ServiceException {
		log.trace("testGetList() invoked.");
		
		
		Criteria cri = new Criteria();
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(1);
		
		List<ReplyVO> list = this.service.getList(cri, dto);
		
		list.forEach(log::info);
		
	} // testGetList
	
	
	@Disabled
	@Test
	@Order(6)
	void testGetTotal() throws ServiceException {
		log.trace("testGetTotal() invoked.");
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(1);
		
		log.info("\t+ total : {}", this.service.getTotal(dto));
		
	} // testGetTotal
	
	
//	댓글 삭제 트랜잭션 테스트
//	@Disabled
	@Test
	@Order(6)
	void testTransactionRemove() throws ServiceException {
		log.trace("testTransactionRemove() invoked.");
		
		BoardDTO bdto = new BoardDTO();
		bdto.setBno(1);
		
		log.info("\t+ Before replycnt : {}", this.service.getTotal(bdto));
		
		ReplyDTO dto = new ReplyDTO();
		dto.setBno(564);
		dto.setRno(null);
		
		log.info("\t+ result : {}", this.service.remove(dto));
		
	} // testGetTotal
	
	
	@AfterAll
	void afterAll() throws Exception {
		BoardDTO dto = new BoardDTO();
		dto.setBno(564);
		
		log.info("\t+ After replycnt : {}", this.service.getTotal(dto));
		
	} // afterAll
	
} // end class
