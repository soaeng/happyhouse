package com.ssafy.happyhouse.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.happyhouse.dao.SurroundingDao;
import com.ssafy.happyhouse.dto.PopulationDto;
import com.ssafy.happyhouse.dto.SurroundingDto;
import com.ssafy.happyhouse.entity.Population;
import com.ssafy.happyhouse.repository.PopulationRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class SurroundingServiceImpl implements SurroundingService{
	
	@Autowired
	SurroundingDao dao;
	
	@Autowired
	PopulationRepository repo;

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

	@Override
	@Transactional
	// 인구 정보 불러오기
	public PopulationDto getPopulation(Map<String, String> param) {
		log.info("===================== 인구 정보 service =============================");
		PopulationDto populationDto = null;
		
		if(repo.existsById(param.get("adstrd"))) {
			populationDto = entity2Dto(repo.getOne(param.get("adstrd")));
		}
		else {
			
			try {
				StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
				urlBuilder.append("/" +  URLEncoder.encode("716c556655736f6437377278657053","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
				urlBuilder.append("/" +  URLEncoder.encode("xml","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
				urlBuilder.append("/" + URLEncoder.encode("SPOP_LOCAL_RESD_DONG","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
				urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
				urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
				// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
				
				// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
				urlBuilder.append("/" + URLEncoder.encode(param.get("date"),"UTF-8")); /* 서비스별 추가 요청인자들*/
				urlBuilder.append("/" + URLEncoder.encode("00","UTF-8")); /* 서비스별 추가 요청인자들*/
				urlBuilder.append("/" + URLEncoder.encode(param.get("adstrd"),"UTF-8")); /* 서비스별 추가 요청인자들*/
				
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/xml");
				System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
				BufferedReader rd;
	
				// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				StringBuilder sb = new StringBuilder();
				String line;
				
				
				while ((line = rd.readLine()) != null) {
					sb.append(line);				
				}
				
				rd.close();
				conn.disconnect();
				
				System.out.println(sb.toString());
				
	
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
				
				String parsingUrl = url.toString();
				Document doc = dBuilder.parse(parsingUrl);
	
				doc.getDocumentElement().normalize();
				
				System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
				NodeList nList = doc.getElementsByTagName("row");
				System.out.println("===================== data parsing ===================");
				
				int len = nList.getLength();
				
				for(int i=0; i<len; i++	) {
					
					Node nNode = (Node) nList.item(i);
					
					if(nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element)nNode;
						
						populationDto = new PopulationDto();
						
						System.out.println("STDR_DE_ID: " + getTagValue("STDR_DE_ID", eElement));
						System.out.println("ADSTRD_CODE_SE: " + getTagValue("ADSTRD_CODE_SE", eElement));
						System.out.println("TOT_LVPOP_CO: " + getTagValue("TOT_LVPOP_CO", eElement));
						
						populationDto.setAdstrdCode(getTagValue("ADSTRD_CODE_SE", eElement));
						populationDto.setTotalLocal(Double.parseDouble(getTagValue("TOT_LVPOP_CO", eElement)));
						populationDto.setMaleTo19(
							Double.parseDouble(getTagValue("MALE_F0T9_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F10T14_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F15T19_LVPOP_CO", eElement))
						);
						populationDto.setMaleTo39(
							Double.parseDouble(getTagValue("MALE_F20T24_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F25T29_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F30T34_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F35T39_LVPOP_CO", eElement))
						);
						populationDto.setMaleTo59(
							Double.parseDouble(getTagValue("MALE_F40T44_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F45T49_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F50T54_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F55T59_LVPOP_CO", eElement))
						);
						populationDto.setMaleTo74(
							Double.parseDouble(getTagValue("MALE_F60T64_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F65T69_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("MALE_F70T74_LVPOP_CO", eElement))
						);
						
						populationDto.setFemaleTo19(
								Double.parseDouble(getTagValue("FEMALE_F0T9_LVPOP_CO", eElement))
								+ Double.parseDouble(getTagValue("FEMALE_F10T14_LVPOP_CO", eElement))
								+ Double.parseDouble(getTagValue("FEMALE_F15T19_LVPOP_CO", eElement))
						);
						populationDto.setFemaleTo39(
							Double.parseDouble(getTagValue("FEMALE_F20T24_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F25T29_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F30T34_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F35T39_LVPOP_CO", eElement))
						);
						populationDto.setFemaleTo59(
							Double.parseDouble(getTagValue("FEMALE_F40T44_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F45T49_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F50T54_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F55T59_LVPOP_CO", eElement))
						);
						populationDto.setFemaleTo74(
							Double.parseDouble(getTagValue("FEMALE_F60T64_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F65T69_LVPOP_CO", eElement))
							+ Double.parseDouble(getTagValue("FEMALE_F70T74_LVPOP_CO", eElement))
						);
						
						populationDto.setTotalMale(populationDto.getMaleTo19() + populationDto.getMaleTo39() + populationDto.getMaleTo59() + populationDto.getMaleTo74());
						populationDto.setTotalFemale(populationDto.getFemaleTo19() + populationDto.getFemaleTo39() + populationDto.getFemaleTo59() + populationDto.getFemaleTo74());
						
						System.out.println(populationDto);
						
						Population entity = dto2Entity(populationDto);
						repo.save(entity);
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return populationDto;
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node)nList.item(0);
		
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}

}
