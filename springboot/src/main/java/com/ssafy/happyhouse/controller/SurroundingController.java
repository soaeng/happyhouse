package com.ssafy.happyhouse.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.ssafy.happyhouse.dto.HouseDto;
import com.ssafy.happyhouse.dto.PopulationDto;
import com.ssafy.happyhouse.dto.PopulationListDto;
import com.ssafy.happyhouse.dto.SurroundingDto;
import com.ssafy.happyhouse.service.SurroundingService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
@Log4j2
public class SurroundingController {
	
	final static String API_KEY = "716c556655736f6437377278657053";

	@Autowired
	SurroundingService service;
	
	// 스벅 목록
	@GetMapping("/house/cafe")
	public ResponseEntity<List<SurroundingDto>> cafeList(HouseDto house){
		// log.info("===== 카페 목록 controller =====");

		Map<String, String> map = new HashMap<String, String>();
		map.put("lat", house.getLat());
		map.put("lng", house.getLng());		
		
		List<SurroundingDto> list = service.cafeList(map);
		
		// list.forEach(cafe -> log.info(cafe));
		
		return new ResponseEntity<List<SurroundingDto>>(list, HttpStatus.OK);
	}

	// 학교 목록
	@GetMapping("/house/school")
	public ResponseEntity<List<SurroundingDto>> schoolList(HouseDto house){
		// log.info("===== 학교 목록 controller =====");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("lat", house.getLat());
		map.put("lng", house.getLng());		
		
		List<SurroundingDto> list = service.schoolList(map);
		
		return new ResponseEntity<List<SurroundingDto>>(list, HttpStatus.OK);
	}

	// 버스 목록
	@GetMapping("/house/bus")
	public ResponseEntity<List<SurroundingDto>> busList(HouseDto house){
		// log.info("===== 학교 목록 controller =====");
		log.info(house);
		Map<String, String> map = new HashMap<String, String>();
		map.put("lat", house.getLat());
		map.put("lng", house.getLng());		
		
		List<SurroundingDto> list = service.busStopList(map);
		
		return new ResponseEntity<List<SurroundingDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/stats/resd")
	public ResponseEntity<PopulationDto> getPopulation(@RequestParam("date") String date, @RequestParam("adstrd") String adstrd) {
		log.info("===== 인구 정보 controller =====");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", date);
		map.put("adstrd", adstrd);		
		
		PopulationDto populationDto = service.getPopulation(map);
		log.info("populationDto: " + populationDto);
		
		return new ResponseEntity<PopulationDto>(populationDto, HttpStatus.OK);
	}
	

	@GetMapping("/stats/resd2")
	public PopulationDto load_save2(@RequestParam("date") String date, @RequestParam("adstrd") String adstrd, Model model) {
		StringBuilder sb = null;
		
		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
			urlBuilder.append("/" +  URLEncoder.encode("716c556655736f6437377278657053","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
			urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
			urlBuilder.append("/" + URLEncoder.encode("SPOP_LOCAL_RESD_DONG","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
			urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
			urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
			// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
			
			// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
			urlBuilder.append("/" + URLEncoder.encode("20220507","UTF-8")); /* 서비스별 추가 요청인자들*/

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
			BufferedReader rd;

			// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			sb = new StringBuilder();
			String line;
			
			
			while ((line = rd.readLine()) != null) {
				sb.append(line);				
			}
			
			rd.close();
			conn.disconnect();
			
			System.out.println(sb.toString());
			
			
			Gson gson = new Gson();
			PopulationListDto list = gson.fromJson(sb.toString(), PopulationListDto.class);
			return null;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}

}
