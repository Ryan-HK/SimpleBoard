package org.zerock.myapp.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class BoardDTO {
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Timestamp update_ts;
	
	private List<BoardAttachDTO> attachList;
	private List<Integer> deleteFile;
} // end class
