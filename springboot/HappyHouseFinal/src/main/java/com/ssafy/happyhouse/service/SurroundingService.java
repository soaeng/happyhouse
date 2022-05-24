package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.SurroundingDto;

public interface SurroundingService {
	
	// 스벅 리스트 불러오기
	public List<SurroundingDto> cafeList(Map<String, String> param);

	// 학교 리스트 불러오기
	public List<SurroundingDto> schoolList(Map<String, String> param);
	
	// 버스 정류장 리스트 불러오기
	public List<SurroundingDto> busStopList(Map<String, String> param);
	
}
