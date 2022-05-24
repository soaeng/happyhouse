package com.ssafy.happyhouse.dto;

import lombok.Data;

@Data
public class AreaCodeDto {
	
	private String code;	// 지역코드
	private String name;	// 지역명
	 
	private String gugunCode;	// 구군코드
	private String gugunName;	// 구군명
	private String cityCode;	// 시도코드
	private String cityName;	// 시도명
	
}
