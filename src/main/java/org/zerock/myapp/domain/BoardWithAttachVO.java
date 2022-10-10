package org.zerock.myapp.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class BoardWithAttachVO {
	
	// tbl_board Property
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Timestamp insert_ts;
	private Timestamp update_ts;
	
	private Integer replyCnt;
	
	// tbl_attach Join Property
	private String uuid;
	private String uploadPath;
	private String fileName;

} // end class
