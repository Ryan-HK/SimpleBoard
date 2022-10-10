package org.zerock.myapp.domain;

import java.util.Date;

import lombok.Value;


@Value
public class ReplyVO {

	private Integer rno;
	private Integer bno;
	
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	
} // end class
