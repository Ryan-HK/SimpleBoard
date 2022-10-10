package org.zerock.myapp.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PageDTO {

	private Criteria cri;
	
	//-- 총 레코드 건수
	private int totalAmount;			
	
	//-- 한 페이지당 페이지 목록의 시작번호
	private int startPage;
	
	//-- 한 페이지당 페이지 목록의 끝번호
	private int endPage;
	
	//-- 총 레코드 건수에 기반한 총 페이지 수
	private int realEndPage;
	
	//-- 현재 페이지에 해당하는 레코드의  시작번호
//	private int offset;
	
	//-- 다음 페이지목록의 존재여부
	private boolean prev;
	//-- 이전 페이지목록의 존재여부
	private boolean next;
	
	public PageDTO(Criteria cri, int totalAmount) {
		
		this.cri = cri;
		this.totalAmount = totalAmount;
		
		//-------------------------------------------------------------
		//-- Step.0 : 페이징 처리를 위한 공통변수 생성
		//-------------------------------------------------------------
		int currPage = cri.getCurrPage();
		int amount = cri.getAmount();
		int pagesPerPage = cri.getPagesPerPage();
		
		
		//-------------------------------------------------------------
		//-- Step.1 : 현재페이지에서 보여줄 페이지목록의 끝페이지 번호
		//-------------------------------------------------------------
		this.endPage = (int) Math.ceil( (currPage*1.0) / pagesPerPage ) * pagesPerPage;
		
		//-------------------------------------------------------------
		//-- Step.2 : 현재페이지에서 보여줄 페이지목록 시작번호구하기
		//-------------------------------------------------------------
		this.startPage = this.endPage - (pagesPerPage -1);
		
		//-------------------------------------------------------------
		//-- Step.3 : 총 페이지수 구하기
		//-------------------------------------------------------------
		this.realEndPage = (int) Math.ceil((totalAmount * 1.0) / amount);
		
		if(this.realEndPage < this.endPage) {
			this.endPage = this.realEndPage;
		} // if
		
		//-------------------------------------------------------------
		//-- Step.4 : 이전 페이지번호목록 이동가능여부 (prev)
		//-------------------------------------------------------------
		this.prev = this.startPage > 1;
		
		//-------------------------------------------------------------
		//-- Step.5 : 다음 페이지번호목록 이동가능여부 (next)
		//-------------------------------------------------------------
		this.next = this.endPage < realEndPage;
		
		//-------------------------------------------------------------
		//-- Step.6 : 현재 페이지에 표시할 목록의 시작 offset 구하기
		//-------------------------------------------------------------
//		this.offset = (currPage - 1) * amount;
		
	} // Constructor
	
	
} // end class
