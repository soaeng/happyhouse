package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.SurroundingDto;

@Mapper
public interface SurroundingDao {
	
	// 해당 동 스벅 리스트 불러오기
	public List<SurroundingDto> cafeList(Map<String, String> param);
	
	// 해당 동 학교 리스트 불러오기
	public List<SurroundingDto> schoolList(Map<String, String> param);
	
	
	// 버스 정류장 리스트 불러오기
	public List<SurroundingDto> busStopList(Map<String, String> param);
	
}
