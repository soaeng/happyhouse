package com.ssafy.happyhouse.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.happyhouse.dto.HouseDto;
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
	
	@PostMapping("/stats/resd")
	public String load_save(@RequestParam("date") String date, @RequestParam("adstrd") String adstrd, Model model) {
		String result = null;
		try {
			String requestDate = date;
			String requestAdstrd = adstrd;
			
			URL url = new URL("http://localhost:8080/" + API_KEY+ "/xml/SPOP_LOCAL_RESD_DONG/1/5/" + date + "/00/ " + adstrd);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			result = br.readLine();
			
			JSONParser jsonParser = new JSONParser(result);
			JsonObject jsonObject = (JsonObject) jsonParser.parse();
			JsonObject resd = (JsonObject) jsonObject.get("resd");
			
			Double totalLocal = Double.parseDouble(resd.get("TOT_LVPOP_CO").toString());
			Double totalMale = Double.parseDouble(resd.get("TOT_LVPOP_CO").toString());
			Double totalFemale = Double.parseDouble(resd.get("TOT_LVPOP_CO").toString());
			
			JsonObject subResult = (JsonObject) resd.get("RESULT");
			JsonArray infoArr = (JsonArray) resd.get("row");
			
			int len = infoArr.size();
			for(int i=0; i<len; i++	) {
				JsonObject tmp = (JsonObject) infoArr.get(i);
				// TODO: substationInfo...
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result; 
	}
}
