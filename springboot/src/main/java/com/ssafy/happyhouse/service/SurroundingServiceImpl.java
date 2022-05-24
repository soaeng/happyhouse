package com.ssafy.happyhouse.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.SurroundingDao;
import com.ssafy.happyhouse.dto.SurroundingDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SurroundingServiceImpl implements SurroundingService{
	
	@Autowired
	SurroundingDao dao;

	// 해당 동 스벅 리스트 불러오기
	@Override
	public List<SurroundingDto> cafeList(Map<String, String> param) {
		// log.info("===== 카페 service =====");
		List<SurroundingDto> list = dao.cafeList(param);
		
		String aptLat = param.get("lat");
		String aptLng = param.get("lng");
		
		for(int i=0; i<list.size(); i++) {
			// apt와 카페 사이 거리 구하기 (단위: km)
			double distance = getDistance(Double.parseDouble(aptLat), Double.parseDouble(aptLng), list.get(i).getLat(), list.get(i).getLng());
//			if(distance > 3) {
//				list.remove(i);
//				i--;
//			}
//			else 
				list.get(i).setDistance(distance * 1000); // 단위 변경(km -> m) 
		}
		
		// 가까운 순으로 정렬
		Collections.sort(list, (l1, l2) -> Double.compare(l1.getDistance(), l2.getDistance()));
		
		// list.forEach(cafe -> log.info(cafe));
		
		return list;
	}

	// 해당 동 학교 리스트 불러오기
	@Override
	public List<SurroundingDto> schoolList(Map<String, String> param) {
		// log.info("===== 학교 service =====");
		List<SurroundingDto> list = dao.schoolList(param);
		/*
		String aptLat = house.getLat();
		String aptLng = house.getLng();
		
		for(int i=0; i<list.size(); i++) {
			// apt와 카페 사이 거리 구하기 (단위: km)
			double distance = getDistance(Double.parseDouble(aptLat), Double.parseDouble(aptLng), list.get(i).getLat(), list.get(i).getLng());
//			if(distance > 3) {
//				list.remove(i);
//				i--;
//			}
//			else 
				list.get(i).setDistance(distance * 1000); // 단위 변경(km -> m) 
		}
		
		// 가까운 순으로 정렬
		Collections.sort(list, (l1, l2) -> Double.compare(l1.getDistance(), l2.getDistance()));
		
		list.forEach(school -> log.info(school));
		*/
		return list;
	}
	
	@Override
	public List<SurroundingDto> busStopList(Map<String, String> param) {
		// log.info("===== 버스 정류장 service =====");
		List<SurroundingDto> list = dao.busStopList(param);
		
		return list;
	}

	// 위도 경도 거리 계산
	public double getDistance(double lat1, double lng1, double lat2, double lng2) {
		if((lat1 == lat2) && (lng1==lng2)) return 0;
		else {
			double theta = lng1 - lng2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			return dist;
		}
	}
}
