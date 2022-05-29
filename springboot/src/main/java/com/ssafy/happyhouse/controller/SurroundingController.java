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
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS}
)
@Log4j2
public class SurroundingController {

    final static String API_KEY = "716c556655736f6437377278657053";

    @Autowired
    SurroundingService service;

    // 스벅 목록
    @GetMapping("/house/cafe")
    public ResponseEntity<List<SurroundingDto>> cafeList(HouseDto house) {
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
    public ResponseEntity<List<SurroundingDto>> schoolList(HouseDto house) {
        // log.info("===== 학교 목록 controller =====");

        Map<String, String> map = new HashMap<String, String>();
        map.put("lat", house.getLat());
        map.put("lng", house.getLng());

        List<SurroundingDto> list = service.schoolList(map);

        return new ResponseEntity<List<SurroundingDto>>(list, HttpStatus.OK);
    }

    // 버스 목록
    @GetMapping("/house/bus")
    public ResponseEntity<List<SurroundingDto>> busList(HouseDto house) {
        // log.info("===== 학교 목록 controller =====");
        log.info(house);
        Map<String, String> map = new HashMap<String, String>();
        map.put("lat", house.getLat());
        map.put("lng", house.getLng());

        List<SurroundingDto> list = service.busStopList(map);

        return new ResponseEntity<List<SurroundingDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/stats/resd")
    public ResponseEntity<PopulationDto> getPopulation(@RequestParam("dongCode") String dongCode) {
        log.info("===== 인구 정보 controller =====");

        String adstrd = service.getAdstrdCode(dongCode);
        PopulationDto populationDto = service.getPopulation(adstrd);
        log.info("populationDto: " + populationDto);

        return new ResponseEntity<PopulationDto>(populationDto, HttpStatus.OK);
    }


    @GetMapping("/stats/resd2")
    public ResponseEntity<String> getPopulationJson(@RequestParam("dongCode") String dongCode) {
        log.info("===== 인구 정보 controller =====");


        String adstrd = service.getAdstrdCode(dongCode);

        String result = service.getPopulationJson(adstrd);
        log.info("population: " + result);

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

}
