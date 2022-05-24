package com.ssafy.happyhouse.dto;

import lombok.Data;

@Data
public class HouseParamDto {
	
	private int limit;			// 가져올 데이터 수
	private int offset;			// 앞에서 생략할 데이터 수
	private String type;		// init: 전체, dong: 동 검색, apt: 아파트 검색, all: 동&아파트 검색

	private String dongCode;	// 동 코드
	private String keyword;		// 검색 키워드
	private int houseNo;		// 아파트번호
	private int userSeq;
}
