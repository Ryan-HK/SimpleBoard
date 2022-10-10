package org.zerock.myapp.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import org.zerock.myapp.exception.DAOException;

import lombok.Cleanup;
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
public class BoardMapperTests {
	
	@Setter(onMethod_= {@Autowired})
	private BoardMapper mapper;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		Objects.requireNonNull(this.mapper);
		log.info("\t+ mapper : {}", this.mapper);
	} // beforeAll
	
	@Disabled
	@Test
	@Order(1)
	@DisplayName("1. BoardMapper.testSelectAllList")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testSelectAllList() throws DAOException {
		log.trace("testSelectAllList() invoked.");
		
		this.mapper.selectAllList().forEach(log::info);
	} // testSelectAllList
	
	@Disabled
	@Test
	@Order(2)
	@DisplayName("2. BoardMapper.testInsert")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testInsert() throws DAOException {
		log.trace("testInsert() invoked.");
		
		BoardDTO dto = new BoardDTO();
		dto.setTitle("TITLE_NEW");
		dto.setContent("CONTENT_NEW");
		dto.setWriter("WRITER_NEW");
		
		log.info("\t+ result : {}", this.mapper.insert(dto));
	} // testInsert
	
	@Disabled
	@Test
	@Order(3)
	@DisplayName("3. BoardMapper.testSelect")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testSelect() throws DAOException {
		log.trace("testSelect() invoked.");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(300);
		
		log.info("\t+ BoardVO : {}", this.mapper.select(dto));
		
	} // testSelect
	
	@Disabled
	@Test
	@Order(4)
	@DisplayName("4. BoardMapper.testUpdate")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testUpdate() throws DAOException {
		log.trace("testUpdate() invoked.");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(80);
		dto.setTitle("TITLE_UPDATE");
		dto.setContent("CONTENT_UPDATE");
		dto.setWriter("WRITER_UPDATE");
		
		log.info("\t+ result : {}", this.mapper.update(dto) == 1);
		
	} // testUpdate
	
	@Disabled
	@Test
	@Order(5)
	@DisplayName("5. BoardMapper.testDelete")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testDelete() throws DAOException {
		log.trace("testDelete() invoked.");
		
		
		log.info("\t+ result : {}", this.mapper.delete(36));
	} // testDelete
	
	@Disabled
	@Test
	@Order(6)
	@DisplayName("6. BoardMapper.selectListWithPaging")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testSelectListWithPaging() throws DAOException {
		log.trace("testSelectListWithPaging() invoked.");
		
		Criteria cri = new Criteria();
		cri.setCurrPage(1);
		cri.setAmount(10);
		
		@Cleanup("clear")
		List<BoardVO> list = this.mapper.selectListWithPaging(cri);
		
		list.forEach(log::info);
	} // testSelectListWithPaging
	
	@Disabled
	@Test
	@Order(7)
	@DisplayName("7. BoardMapper.getTotalCount")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGetTotalCount() throws DAOException {
		log.trace("testGetTotalCount() invoked.");
		
		log.info("\t+ totalCount : {}", this.mapper.getTotalCount());
	} // testSelectListWithPaging
	
	
	@Disabled
	@Test
	@Order(8)
	@DisplayName("8. BoardMapper.getCurrentTime")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGetCurrentTime() throws DAOException {
		log.trace("testGetTotalCount() invoked.");
		
		log.info("\t+ CurrentTime : {}", this.mapper.getCurrentTime());
		
		Date time1 = this.mapper.getCurrentTime();
		BoardDTO dto = new BoardDTO();
		dto.setBno(20);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 게시글 작성시간
		String boardTime = sdf.format(this.mapper.select(dto).getInsert_ts());
		// 현재 시각
		String currTime = sdf.format(this.mapper.getCurrentTime());
		log.info(boardTime);
		log.info(currTime);
		log.info("\t+ boardTime : {}", Integer.valueOf(currTime)-Integer.valueOf(boardTime));
		
	} // testSelectListWithPaging
	
	
//	@Disabled
	@Test
	@Order(9)
	@DisplayName("8. BoardMapper.getTotalCountReply")
	@Timeout(value=3, unit=TimeUnit.SECONDS)
	void testGetTotalCountReply() throws DAOException {
		log.trace("testGetTotalCountReply() invoked.");
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(1);
		
		log.info("\t+ result : {}", this.mapper.getTotalCountReply(dto));
		
		
	} // testGetTotalCountReply
	

} // end class
