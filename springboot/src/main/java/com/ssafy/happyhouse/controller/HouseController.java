package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.BookmarkDto;
import com.ssafy.happyhouse.dto.HouseDto;
import com.ssafy.happyhouse.dto.HouseParamDto;
import com.ssafy.happyhouse.dto.HouseResultDto;
import com.ssafy.happyhouse.dto.UserDto;
import com.ssafy.happyhouse.service.HouseService;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
@Log4j2
public class HouseController {
	
	@Autowired
	HouseService service;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@GetMapping("/house/deal")
	public ResponseEntity<HouseResultDto> houseList(HouseParamDto dto) {
		
		// log.info("===== 아파트 매매 정보 조회 메인 controller =====");
		HouseResultDto resultDto = new HouseResultDto();
		switch(dto.getType()) {

			// 전체 매매 정보 조회
			case "init":
				// log.info("----- 전체 매매 정보 조회 controller -----");
				List<HouseDto> dealList = service.initAptList(dto);
				
				// 전체 거래 데이터 수
				int dealCnt = service.initAptListTotalCount();
				
				if(dealList != null) {
					resultDto.setList(dealList);
					resultDto.setCount(dealCnt);
					resultDto.setResult(SUCCESS);
					
					// 가져온 거래 정보 확인
					// dealList.forEach(deal -> log.info(deal));
					// log.info("전체 거래 데이터 수: " + dealCnt);
				} else {
					resultDto.setResult(FAIL);
				}
				break;
				
				
			// 동별 매매 정보 조회
			case "dong":
				// log.info("----- 동별 매매 정보 조회 controller -----");
				List<HouseDto> dongList = service.dongAptList(dto);
				// 동별 거래 데이터 수
				int dongCnt = service.dongAptListTotalCount(dto.getDongCode());
				
				if(dongList != null) {
					resultDto.setList(dongList);
					resultDto.setCount(dongCnt);
					resultDto.setResult(SUCCESS);
					
					// 가져온 거래 정보 확인
					// list.forEach(deal -> log.info(deal));
					// log.info("동별 거래 데이터 수: " + dongCnt);
				} else {
					// log.info("데이터 없음");
					resultDto.setResult(FAIL);
				}
				break;
			
				
			// 아파트 정보 조회
			case "name":
				// log.info("----- 아파트 정보 조회 controller -----");
				List<HouseDto> aptList = service.nameAptList(dto);
				
				// 아파트별 거래 데이터 수
				int aptCnt = service.nameAptListTotalCount(dto.getKeyword());
				
				if(aptList != null) {
					resultDto.setList(aptList);
					resultDto.setCount(aptCnt);
					resultDto.setResult(SUCCESS);
					
					// 가져온 거래 정보 확인
					// aptList.forEach(apt -> log.info(apt));
					// log.info("아파트 검색 데이터 수: " + aptCnt);
				} else {
					resultDto.setResult(FAIL);
				}
				break;
				

			// 동, 아파트 둘 다 조회
			case "all":
				// log.info("----- 동 아파트 정보 조회 controller -----");
				List<HouseDto> dongAptList = service.allAptList(dto);
				
				Map<String, String> parameter = new HashMap<String, String>();
				parameter.put("dongCode", dto.getDongCode());
				parameter.put("keyword", dto.getKeyword());
				
				// 데이터 수
				int dongAptCnt = service.allAptListTotalCount(parameter);
				
				if(dongAptList != null) {
					resultDto.setList(dongAptList);
					resultDto.setCount(dongAptCnt);
					resultDto.setResult(SUCCESS);
					
					// 가져온 거래 정보 확인
					// dongAptList.forEach(deal -> log.info(deal));
					// log.info("동 아파트 검색 데이터 수: " + dongAptCnt);
				} else {
					resultDto.setResult(FAIL);
				}
				break;
				
		}
		
		if( resultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<HouseResultDto>(resultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<HouseResultDto>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	} // end of houseList
		
	@GetMapping("/house/deal/{houseNo}")
	public ResponseEntity<HouseResultDto> aptDealList(HouseParamDto dto, HttpSession session){
		 log.info("===== 아파트별 거래 목록 controller =====");
		
		dto.setUserSeq(((UserDto) session.getAttribute("userDto")).getUserSeq());
		log.info(">>>>>> /house/deal/{houseNo}: " + dto.getUserSeq());
		HouseResultDto resultDto = new HouseResultDto();
		List<HouseDto> aptDealList = service.aptDealList(dto);
		
		// 데이터 수
		int aptDealCnt = service.aptDealListTotalCount(dto.getHouseNo());
		
		if(aptDealList != null) {
			// aptDealList.forEach(deal -> log.info(deal));
			resultDto.setList(aptDealList);
			resultDto.setCount(aptDealCnt);
			resultDto.setResult(SUCCESS);
			
			// 가져온 거래 정보 확인
			// log.info("아파트별 거래 데이터 수: " + aptDealCnt);
		} else {
			resultDto.setResult(FAIL);
		}

		if( resultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<HouseResultDto>(resultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<HouseResultDto>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/house/map/{lat}/{lng}")
	public ResponseEntity<Map<String, String>> map(@PathVariable String lat, @PathVariable String lng){
		// log.info("===== 지도 좌표 controller =====");
		Map<String, String> map = new HashMap<String, String>();
		map.put("lat", lat);
		map.put("lng", lng);
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
	
	
	// 시도 코드
	@GetMapping("/sido")
	public ResponseEntity<List<AreaCodeDto>> sidoList(){
		// log.info("===== 시도코드 controller =====");
		List<AreaCodeDto> sidoList = service.sidoList();
		
		if(sidoList != null) {
			// sidoList.forEach(sido -> log.info(sido));
			return new ResponseEntity<List<AreaCodeDto>>(sidoList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<AreaCodeDto>>(sidoList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 구군 코드
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<List<AreaCodeDto>> gugunList(@PathVariable("sidoCode") String sidoCode) {
		// log.info("===== 구군코드 controller =====");
		List<AreaCodeDto> gugunList = service.gugunList(sidoCode);
		
		if(gugunList != null) {
			// gugunList.forEach(sido -> log.info(sido));
			return new ResponseEntity<List<AreaCodeDto>>(gugunList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<AreaCodeDto>>(gugunList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 동 코드
	@GetMapping("/dong/{gugunCode}")
	public ResponseEntity<List<AreaCodeDto>> dongList(@PathVariable("gugunCode") String gugunCode) {
		// log.info("===== 동코드 controller =====");
		List<AreaCodeDto> dongList = service.dongList(gugunCode);
		
		if(dongList != null) {
			// dongList.forEach(sido -> log.info(sido));
			return new ResponseEntity<List<AreaCodeDto>>(dongList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<AreaCodeDto>>(dongList, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// 북마크
	// 관심지역 등록
	@PostMapping("/bookmark/area")
	public ResponseEntity<Integer> insertBookmarkArea(BookmarkDto bookmark, HttpSession session){
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		int result = service.insertBookmarkArea(bookmark);
		if(result == 1) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 관심지역 조회
	@GetMapping("/bookmark/area")
	public ResponseEntity<List<AreaCodeDto>> bookmarkAreaList(BookmarkDto bookmark, HttpSession session){
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		List<AreaCodeDto> areaList = service.bookmarkAreaList(bookmark.getUserSeq());
		if(areaList != null) {
			return new ResponseEntity<List<AreaCodeDto>>(areaList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<AreaCodeDto>>(areaList, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	// 관심지역 삭제
	@DeleteMapping("/bookmark/area")
	public ResponseEntity<Integer> deleteBookmarkArea(BookmarkDto bookmark, HttpSession session){
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		int result = service.deleteBookmarkArea(bookmark);
		if(result == 1) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	
//	// 관심아파트 등록
//	@PostMapping("/bookmark/house")
//	public ResponseEntity<Integer> insertBookmarkHouse(BookmarkDto bookmark, HttpSession session){
//		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
//		int result = service.insertBookmarkHouse(bookmark);
//		if(result == 1) {
//			return new ResponseEntity<Integer>(result, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	// 관심아파트 조회
//	@GetMapping("/bookmark/house")
//	public ResponseEntity<List<BookmarkDto>> bookmarkHouseList(BookmarkDto bookmark, HttpSession session){
//		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
//		List<BookmarkDto> houseList = service.bookmarkHouseList(bookmark.getUserSeq());
//		if(houseList != null) {
//			return new ResponseEntity<List<BookmarkDto>>(houseList, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<List<BookmarkDto>>(houseList, HttpStatus.INTERNAL_SERVER_ERROR);			
//		}
//	}
//	
//	// 관심아파트 삭제
//	@DeleteMapping("/bookmark/house")
//	public ResponseEntity<Integer> deleteBookmarkHouse(BookmarkDto bookmark, HttpSession session){
//		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
//		int result = service.deleteBookmarkHouse(bookmark);
//		if(result == 1) {
//			return new ResponseEntity<Integer>(result, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	
	// 관심거래 등록
	@PostMapping("/bookmark/deal")
	public ResponseEntity<Integer> insertBookmarkDeal(BookmarkDto bookmark, HttpSession session){
		System.out.println("========================================");
		log.info(((UserDto) session.getAttribute("userDto")).getUserSeq());
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		log.info(bookmark);
		int result = service.insertBookmarkDeal(bookmark);
		if(result == 1) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 관심거래 조회
	@GetMapping("/bookmark/deal")
	public ResponseEntity<List<HouseDto>> bookmarkDealList(BookmarkDto bookmark, HttpSession session){
		System.out.println("========================================");
		log.info(((UserDto) session.getAttribute("userDto")).getUserSeq());
		//return null;
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		log.info(bookmark.getUserSeq());
		List<HouseDto> dealList = service.bookmarkDealList(bookmark.getUserSeq());
		if(dealList != null) {
			return new ResponseEntity<List<HouseDto>>(dealList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<HouseDto>>(dealList, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	// 관심거래 삭제
	@DeleteMapping("/bookmark/deal")
	public ResponseEntity<Integer> deleteBookmarkDeal(BookmarkDto bookmark, HttpSession session){
		bookmark.setUserSeq( ((UserDto) session.getAttribute("userDto")).getUserSeq());
		int result = service.deleteBookmarkDeal(bookmark);
		if(result == 1) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
