package com.ssafy.happyhouse.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.happyhouse.dto.BookmarkDto;
import com.ssafy.happyhouse.dto.HouseParamDto;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class HouseServiceTests {

    final String sidoCode = "11";
    final String gugunCode = "11110";
    final String dongCode = "1111011500";
    final String dongName = "사직동";
    final String keyword = "풍림";
    final int houseNo = 1;
    final int dealNo = 1;
    final int userSeq = 1;

    @Autowired
    private HouseService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }


    // 시도코드
    // @Test
    void testSidoList() {
        log.info("===== 시도 목록 =====");
        service.sidoList().forEach(sido -> log.info(sido));
    }


    // 구군코드
    // @Test
    void testGugunList() {
        log.info("===== 구군 목록 =====");
        service.gugunList(sidoCode).forEach(gugun -> log.info(gugun));
    }


    // 동코드
    // @Test
    void testDongList() {
        log.info("===== 동 목록 =====");
        service.dongList(gugunCode).forEach(dong -> log.info(dong));
    }

    // 동조회
    // @Test
    void testDongDetail() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("gugunCode", gugunCode);
        map.put("name", dongName);
        log.info("===== 동 조회: " + service.dongDetail(map));
    }


    // 전체 아파트 목록
    // @Test
    void testDealList() {
        log.info("==== 아파트 전체 목록 ====");
        HouseParamDto param = new HouseParamDto();
        param.setLimit(10);
        param.setOffset(0);
        service.initAptList(param).forEach(apt -> log.info(apt));
    }

    // 아파트 총 수
    // @Test
    void testInitAptListTotalCount() {
        log.info("===== 아파트 총 수: " + service.initAptListTotalCount());
    }

    // 동별 아파트 검색 목록
    // @Test
    void testDongAptList() {
        log.info("===== 동별 아파트 검색 목록(" + dongCode + ") =====");
        HouseParamDto param = new HouseParamDto();
        param.setLimit(10);
        param.setOffset(0);
        param.setDongCode(dongCode);
        service.dongAptList(param).forEach(apt -> log.info(apt));
    }

    // 동별 아파트 검색 총 수
    // @Test
    void testDongAptListTotalCount() {
        log.info("===== 동 검색 총 수: " + service.dongAptListTotalCount(dongCode));
    }


    // 매물 아파트명 검색 목록
    // @Test
    void testNameAptList() {
        log.info("===== 아파트명 검색 목록(" + keyword + ") =====");
        HouseParamDto param = new HouseParamDto();
        param.setLimit(10);
        param.setOffset(0);
        param.setKeyword(keyword);
        service.nameAptList(param).forEach(apt -> log.info(apt));
    }

    // 아파트 검색 총 수
    // @Test
    void testNameAptListTotalCount() {
        log.info("===== 아파트 검색 총 수: " + service.nameAptListTotalCount(keyword));
    }

    // 동 아파트명 검색 목록
    // @Test
    void testAllAptList() {
        log.info("===== 동 아파트명 검색 목록(" + dongCode + ", " + keyword + ") =====");
        HouseParamDto param = new HouseParamDto();
        param.setLimit(10);
        param.setOffset(0);
        param.setDongCode(dongCode);
        param.setKeyword(keyword);
        service.allAptList(param).forEach(apt -> log.info(apt));
    }

    // 동 아파트 검색 총 수
    // @Test
    void testAllAptListTotalCount() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("dongCode", dongCode);
        param.put("keyword", keyword);
        log.info("===== 동 아파트 검색 총 수: " + service.allAptListTotalCount(param));
    }

    // 아파트별 거래 상세 목록
    // @Test
    void testAptDealList() {
        log.info("===== 아파트별 거래 상세(" + houseNo + ") =====");
        HouseParamDto param = new HouseParamDto();
        param.setLimit(10);
        param.setOffset(0);
        param.setHouseNo(houseNo);
        service.aptDealList(param).forEach(deal -> log.info(deal));
    }

    // 아파트별 거래 수
    // @Test
    void testAptDealListTotalCount() {
        log.info("===== 아파트별 거래 수: " + service.aptDealListTotalCount(houseNo));
    }

    // 관심지역 등록
    // @Test
    void testInsertBookmarkArea() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setDongCode(dongCode);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심지역 등록 수 : " + service.insertBookmarkArea(bookmark));
    }

    // 관심지역 조회
    // @Test
    void testBookmarkAreaList() {
        log.info(">>>>> 관심지역 리스트 조회 <<<<<");
        service.bookmarkAreaList(userSeq).forEach(area -> log.info(area));
    }

    // 관심지역 삭제
    // @Test
    void testDeleteBookmarkArea() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setDongCode(dongCode);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심지역 삭제 수 : " + service.deleteBookmarkArea(bookmark));
    }

    // 관심아파트 등록
    // @Test
    void testInsertBookmarkHouse() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setHouseNo(houseNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심아파트 등록 수 : " + service.insertBookmarkHouse(bookmark));
    }

    // 관심아파트 조회
    // @Test
    void testBookmarkHouseList() {
        log.info(">>>>> 관심아파트 리스트 조회 <<<<<");
        service.bookmarkHouseList(userSeq).forEach(house -> log.info(house));
    }

    // 관심아파트 여부
    // @Test
    void testCheckBookmarkHouse() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setHouseNo(houseNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심아파트 등록 여부 : " + service.checkBookmarkHouse(bookmark));
    }

    // 관심아파트 삭제
    // @Test
    void testDeleteBookmarkHouse() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setHouseNo(houseNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심아파트 삭제 수 : " + service.deleteBookmarkHouse(bookmark));
    }


    // 관심거래 등록
    // @Test
    void testInsertBookmarkDeal() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setDealNo(dealNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심거래 등록 수 : " + service.insertBookmarkDeal(bookmark));
    }

    // 관심거래 조회
    // @Test
    void testBookmarkDealList() {
        log.info(">>>>> 관심거래 리스트 조회 <<<<<");
        service.bookmarkDealList(userSeq).forEach(deal -> log.info(deal));
    }

    // 관심거래 여부
    // @Test
    void testCheckBookmarkDeal() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setDealNo(dealNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심거래 등록 여부: " + service.checkBookmarkDeal(bookmark));
    }

    // 관심거래 삭제
    // @Test
    void testDeleteBookmarkDeal() {
        BookmarkDto bookmark = new BookmarkDto();
        bookmark.setDealNo(dealNo);
        bookmark.setUserSeq(userSeq);
        log.info(">>>>>>> 관심거래 삭제 수: " + service.deleteBookmarkDeal(bookmark));
    }
}
