package org.zerock.myapp.domain;

import lombok.Data;

@Data
public class BoardAttachVO {

	private Integer attach_no;
	private String uuid;
	private String uploadPath;
	private String fileName;
	
	private Integer bno;
	
} // end class
