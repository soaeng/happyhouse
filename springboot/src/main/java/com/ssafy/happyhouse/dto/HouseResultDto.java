package com.ssafy.happyhouse.dto;

import java.util.List;

import lombok.Data;

@Data
public class HouseResultDto {

	private int result;				// 성공여부
	private HouseDto dto;			// 아파트 정보
	private List<HouseDto> list;	// 아파트 목록
	private int count;				// 가져온 데이터 수

}
