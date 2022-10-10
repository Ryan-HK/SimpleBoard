package org.zerock.myapp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Value;

@Value
public class BoardVO {
	
	// tbl_board Property
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Timestamp insert_ts;
	private Timestamp update_ts;
	
	private Integer replyCnt;
	
	private List<BoardAttachVO> list;	
} // end class
