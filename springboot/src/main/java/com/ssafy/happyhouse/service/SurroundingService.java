package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.PopulationDto;
import com.ssafy.happyhouse.dto.SurroundingDto;
import com.ssafy.happyhouse.entity.Population;

public interface SurroundingService {
	
	// 스벅 리스트 불러오기
	public List<SurroundingDto> cafeList(Map<String, String> param);

	// 학교 리스트 불러오기
	public List<SurroundingDto> schoolList(Map<String, String> param);
	
	// 버스 정류장 리스트 불러오기
	public List<SurroundingDto> busStopList(Map<String, String> param);
	
	// 인구 정보 불러오기
	public PopulationDto getPopulation(Map<String, String> param);
	
	default Population dto2Entity(PopulationDto dto) {
		Population entity = Population.builder()
				.adstrdCode(dto.getAdstrdCode())
				.totalLocal(dto.getTotalLocal())
				.totalMale(dto.getTotalMale())
				.maleTo19(dto.getMaleTo19())
				.maleTo39(dto.getMaleTo39())
				.maleTo59(dto.getMaleTo59())
				.maleTo74(dto.getMaleTo74())
				.totalFemale(dto.getTotalFemale())
				.femaleTo19(dto.getFemaleTo19())
				.femaleTo39(dto.getFemaleTo39())
				.femaleTo59(dto.getFemaleTo59())
				.femaleTo74(dto.getFemaleTo74())
				.build();
		return entity;
	}
	
	default PopulationDto entity2Dto(Population entity) {
		PopulationDto dto = PopulationDto.builder()
				.adstrdCode(entity.getAdstrdCode())
				.totalLocal(entity.getTotalLocal())
				.totalMale(entity.getTotalMale())
				.maleTo19(entity.getMaleTo19())
				.maleTo39(entity.getMaleTo39())
				.maleTo59(entity.getMaleTo59())
				.maleTo74(entity.getMaleTo74())
				.totalFemale(entity.getTotalFemale())
				.femaleTo19(entity.getFemaleTo19())
				.femaleTo39(entity.getFemaleTo39())
				.femaleTo59(entity.getFemaleTo59())
				.femaleTo74(entity.getFemaleTo74())
				.build();
		return dto;
	}
}
