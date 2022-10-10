package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Data;


@Data
public class ReplyDTO {

	private Integer rno;
	private Integer bno;
	
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	
} // end class
