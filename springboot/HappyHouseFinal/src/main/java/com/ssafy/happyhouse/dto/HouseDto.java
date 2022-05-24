package com.ssafy.happyhouse.dto;

import lombok.Data;

@Data
public class HouseDto {
	
	private int no;				// 거래 번호
	private String dong;		// 동 이름
	private String AptName;		// 아파트명
	private String code;		// 구군 코드
	private String dealAmount;	// 거래 금액
	private String buildYear;	// 건축년도
	private String dealYear;	// 거래년도
	private String dealMonth;	// 거래 월
	private String dealDay;		// 거래 일
	private String area;		// 면적
	private String floor;		// 층
	private String lat;			// 위도
	private String lng;			// 경도
	private int houseNo;		// 건물 번호
	
	private boolean bookmark;	// 북마크 여부
	
	private String address;		// 시 군 동
	
}
