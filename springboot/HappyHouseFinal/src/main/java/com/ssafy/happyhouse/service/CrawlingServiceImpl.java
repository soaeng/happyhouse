package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.component.JsoupComponent;
import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.NewsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrawlingServiceImpl {

	private final JsoupComponent jsoupComponent;
	
	public List<NewsDto> getNewsList() {
		AreaCodeDto dto = null;
		return jsoupComponent.getNewsList(dto);
	}
	
	
	public List<NewsDto> getNewsList(AreaCodeDto dto) {
		return jsoupComponent.getNewsList(dto);
	}
	
}
