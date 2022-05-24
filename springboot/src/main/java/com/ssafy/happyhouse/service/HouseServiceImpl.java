package com.ssafy.happyhouse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.HouseDao;
import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.BookmarkDto;
import com.ssafy.happyhouse.dto.HouseDto;
import com.ssafy.happyhouse.dto.HouseParamDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HouseServiceImpl implements HouseService {

	@Autowired
	HouseDao dao;
	
	// 시도코드
	@Override
	public List<AreaCodeDto> sidoList() {
		// log.info("===== 시도 목록 service =====");
		return dao.sidoList();
	}

	// 구군코드
	@Override
	public List<AreaCodeDto> gugunList(String sidoCode) {
		// log.info("===== 구군 목록 service =====");
		return dao.gugunList(sidoCode);
	}

	// 동코드
	@Override
	public List<AreaCodeDto> dongList(String gugunCode) {
		// log.info("===== 동 목록 service =====");
		return dao.dongList(gugunCode);
	}

	// 동 조회
	@Override
	public AreaCodeDto dongDetail(Map<String, String> param) {
		// log.info("===== 동 조회 service =====");
		return dao.dongDetail(param);
	}
	
	// 총 거래 목록
	@Override
	public List<HouseDto> initAptList(HouseParamDto param) {
		// log.info("===== 총 거래 목록 service =====");
		
		List<HouseDto> list = dao.initAptList(param);
		
		list.forEach(dto ->{
			Map<String, String> map = new HashMap<String, String>();
			map.put("gugunCode", dto.getCode());
			map.put("name", dto.getDong());
			
			AreaCodeDto dongDto = dao.dongDetail(map);
			dto.setAddress(dongDto.getCityName() + " " + dongDto.getGugunName() + " " + dongDto.getName());
			
			BookmarkDto bookmark = new BookmarkDto();
			bookmark.setHouseNo(dto.getHouseNo());
			bookmark.setUserSeq(param.getUserSeq());
			dto.setBookmark(dao.checkBookmarkHouse(bookmark));
		});
		
		return list;
	}

	// 총 거래 수
	@Override
	public int initAptListTotalCount() {
		// log.info("===== 총 거래 수 service =====");
		return dao.initAptListTotalCount();
	}

	// 동 검색
	@Override
	public List<HouseDto> dongAptList(HouseParamDto param) {
		// log.info("===== 동 검색 service =====");
		
		List<HouseDto> list = dao.dongAptList(param);
		list.forEach(dto ->{
			Map<String, String> map = new HashMap<String, String>();
			map.put("gugunCode", dto.getCode());
			map.put("name", dto.getDong());
			AreaCodeDto dongDto = dao.dongDetail(map);
			dto.setAddress(dongDto.getCityName() + " " + dongDto.getGugunName() + " " + dongDto.getName());

			BookmarkDto bookmark = new BookmarkDto();
			bookmark.setHouseNo(dto.getHouseNo());
			bookmark.setUserSeq(param.getUserSeq());
			dto.setBookmark(dao.checkBookmarkHouse(bookmark));
			
		});
		
		return list;
	}

	// 동 검색 결과 수
	@Override
	public int dongAptListTotalCount(String keyword) {
		// log.info("===== 동 검색 결과 수 service =====");
		return dao.dongAptListTotalCount(keyword);
	}

	// 아파트 검색
	@Override
	public List<HouseDto> nameAptList(HouseParamDto param) {
		// log.info("===== 아파트 검색 service =====");

		List<HouseDto> list = dao.nameAptList(param);
		list.forEach(dto ->{
			Map<String, String> map = new HashMap<String, String>();
			map.put("gugunCode", dto.getCode());
			map.put("name", dto.getDong());
			AreaCodeDto dongDto = dao.dongDetail(map);
			dto.setAddress(dongDto.getCityName() + " " + dongDto.getGugunName() + " " + dongDto.getName());

			BookmarkDto bookmark = new BookmarkDto();
			bookmark.setHouseNo(dto.getHouseNo());
			bookmark.setUserSeq(param.getUserSeq());
			dto.setBookmark(dao.checkBookmarkHouse(bookmark));
			
		});
		
		return list;
	}

	// 아파트 검색 결과 수
	@Override
	public int nameAptListTotalCount(String keyword) {
		// log.info("===== 아파트 검색 결과 수 service =====");
		return dao.nameAptListTotalCount(keyword);
	}
	
	// 동 아파트 검색 목록
	@Override
	public List<HouseDto> allAptList(HouseParamDto param){
		// log.info("===== 동 아파트 검색 service =====");

		List<HouseDto> list = dao.allAptList(param);
		list.forEach(dto ->{
			Map<String, String> map = new HashMap<String, String>();
			map.put("gugunCode", dto.getCode());
			map.put("name", dto.getDong());
			AreaCodeDto dongDto = dao.dongDetail(map);
			dto.setAddress(dongDto.getCityName() + " " + dongDto.getGugunName() + " " + dongDto.getName());

			BookmarkDto bookmark = new BookmarkDto();
			bookmark.setHouseNo(dto.getHouseNo());
			bookmark.setUserSeq(param.getUserSeq());
			dto.setBookmark(dao.checkBookmarkHouse(bookmark));
			
		});
		
		return list;
	}
	
	// 동 아파트 검색 총 수
	@Override
	public int allAptListTotalCount(Map<String, String> param) {
		// log.info("===== 아파트 검색 결과 수 service =====");
		return dao.allAptListTotalCount(param);
	}
	
	// 아파트별 거래 목록
	@Override
	public List<HouseDto> aptDealList(HouseParamDto param) {
		// log.info("===== 아파트별 거래 목록 service =====");

		List<HouseDto> list = dao.aptDealList(param.getHouseNo());
		list.forEach(dto ->{
			Map<String, String> map = new HashMap<String, String>();
			map.put("gugunCode", dto.getCode());
			map.put("name", dto.getDong());
			AreaCodeDto dongDto = dao.dongDetail(map);
			dto.setAddress(dongDto.getCityName() + " " + dongDto.getGugunName() + " " + dongDto.getName());

			BookmarkDto bookmark = new BookmarkDto();
			bookmark.setDealNo(dto.getNo());
			bookmark.setUserSeq(param.getUserSeq());
			dto.setBookmark(dao.checkBookmarkDeal(bookmark));
			log.info(dto.getNo() + " " + param.getUserSeq() + " " + dto.isBookmark());
			
		});
				
		return list;
	}

	// 아파트별 거래 총 수
	@Override
	public int aptDealListTotalCount(int houseNo) {
		// log.info("===== 아파트별 거래 총 수 service =====");
		return dao.aptDealListTotalCount(houseNo);
	}

	
	/* 북마크 */
	// 관심지역 등록
	@Override
	public int insertBookmarkArea(BookmarkDto bookmark) {
		return dao.insertBookmarkArea(bookmark);
	}
	
	// 관심지역 조회
	@Override
	public List<AreaCodeDto> bookmarkAreaList(int userSeq) {
		return dao.bookmarkAreaList(userSeq);
	}
	
	// 관심지역 삭제
	@Override
	public int deleteBookmarkArea(BookmarkDto bookmark) {
		return dao.deleteBookmarkArea(bookmark);
	}

	
	// 관심아파트 등록
	@Override
	public int insertBookmarkHouse(BookmarkDto bookmark) {
		return dao.insertBookmarkHouse(bookmark);
	}

	// 관심아파트 조회
	@Override
	public List<BookmarkDto> bookmarkHouseList(int userSeq) {
		return dao.bookmarkHouseList(userSeq);
	}

	// 관심아파트 여부
	@Override
	public boolean checkBookmarkHouse(BookmarkDto bookmark) {
		return dao.checkBookmarkHouse(bookmark);
	}

	// 관심아파트 삭제
	@Override
	public int deleteBookmarkHouse(BookmarkDto bookmark) {
		return dao.deleteBookmarkHouse(bookmark);
	}

	
	// 관심거래 등록
	@Override
	public int insertBookmarkDeal(BookmarkDto bookmark) {
		return dao.insertBookmarkDeal(bookmark);
	}

	// 관심거래 조회
	@Override
	public List<HouseDto> bookmarkDealList(int userSeq) {
		return dao.bookmarkDealList(userSeq);
	}

	// 관심거래 여부
	@Override
	public boolean checkBookmarkDeal(BookmarkDto bookmark) {
		return dao.checkBookmarkDeal(bookmark);
	}

	// 관심거래 삭제
	@Override
	public int deleteBookmarkDeal(BookmarkDto bookmark) {
		return dao.deleteBookmarkDeal(bookmark);
	}
}
