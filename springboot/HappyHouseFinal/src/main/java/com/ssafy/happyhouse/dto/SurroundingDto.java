package com.ssafy.happyhouse.dto;

import lombok.Data;

@Data
public class SurroundingDto {
	private String name;		// 이름
	private String grade;		// 학교구분
	private String address;		// 주소
	private double lat;			// 위도
	private double lng;			// 경도
	
	private double distance;	// 거리
}
