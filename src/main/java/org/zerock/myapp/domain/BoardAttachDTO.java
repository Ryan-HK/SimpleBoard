package org.zerock.myapp.domain;

import lombok.Data;

@Data
public class BoardAttachDTO implements Cloneable {

	private Integer attach_no;
	private String uuid;
	private String uploadPath;
	private String fileName;
	
	private Integer bno;

	
	@Override
	public BoardAttachDTO clone() throws CloneNotSupportedException {
		return (BoardAttachDTO)super.clone();
	} // clone
	
} // end class
