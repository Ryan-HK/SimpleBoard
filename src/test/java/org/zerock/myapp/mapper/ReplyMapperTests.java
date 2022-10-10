package org.zerock.myapp.mapper;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
import org.zerock.myapp.domain.Criteria;
import org.zerock.myapp.domain.ReplyDTO;
import org.zerock.myapp.domain.ReplyVO;
import org.zerock.myapp.exception.DAOException;
import org.zerock.myapp.exception.ServiceException;

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
public class ReplyMapperTests {

	@Setter(onMethod_= {@Autowired})
	private ReplyMapper mapper;
	
	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. ReplyMapper.insert()")
	void testInsert() throws DAOException {
		log.trace("testInsert() invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		dto.setBno(1);
		dto.setReply("안녕하세요!");
		dto.setReplyer("Ryan");
		
		log.info("\t+ ReplyDTO : {}", this.mapper.insert(dto));
		
	} // testInsert
	
	@Disabled
	@Test
	@Order(5)
	@DisplayName("2. ReplyMapper.read()")
	void testRead() throws DAOException {
		log.trace("testRead() invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		dto.setRno(2);
		
		log.info("\t+ myReply : {}", this.mapper.read(dto));
			
	} // testRead
	
	@Disabled
	@Test
	@Order(3)
	@DisplayName("3. ReplyMapper.delete()")
	void testDelete() throws  DAOException {
		log.trace("testDelete() invoked.");
		
		log.info("\t+ delete : {}", this.mapper.delete(1));
	} // testDelete
	
	
	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. ReplyMapper.update()")
	void testUpdate() throws DAOException {
		log.trace("testUpdate() invoked.");
		
		ReplyDTO dto = new ReplyDTO();
		dto.setRno(2);
		dto.setReply("수정 리플 안녕하세요");

		log.info("\t+ update : [}", this.mapper.update(dto));
		
	} // testUpdate
	
//	@Disabled
	@Test
	@Order(6)
	@DisplayName("5. ReplyMapper.getListWithPaging")
	void testGetListWithPaging() throws DAOException {
		log.trace("testGetListWithPaging() invoked.");
		
		Criteria cri = new Criteria();
		
		Integer bno = 1;
		
		List<ReplyVO> list = this.mapper.getListWithPaging(cri, bno);
		
		list.forEach(log::info);
		
	} // testGetListWithPaging
	
	@Test
	@Order(7)
	@DisplayName("6. ReplyMapper.getTotalCount()") 
	void testGetTotalCount() throws DAOException {
		log.trace("testGetTotalCount() invoked.");
		
		Integer bno = 1;
		
		log.info("\t+ getTotalCount : {}", this.mapper.getTotalCount(bno));
		
	} // 
	
	
} // end class
