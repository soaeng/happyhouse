package com.ssafy.happyhouse.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class HouseDaoTests {

    final String sidoCode = "11";
    final String gugunCode = "11110";
    final String dongCode = "1111011500";
    final String dongName = "사직동";
    final String keyword = "풍림";
    final int houseNo = 1;

    @Autowired
    private HouseDao dao;

    // @Test
    void contextLoads() {
        assertNotNull(dao);
    }

    // 시도코드
    // @Test
    void testSidoList() {
        log.info("===== 시도 목록 =====");
        dao.sidoList().forEach(sido -> log.info(sido));
    }

    // 구군코드
    // @Test
    void testGugunList() {
        log.info("===== 구군 목록 =====");
        dao.gugunList(sidoCode).forEach(gugun -> log.info(gugun));
    }

    // 동코드
    @Test
    void testDongList() {
        log.info("===== 동 목록 =====");
        dao.dongList(gugunCode).forEach(dong -> log.info(dong));
    }

    // 동조회
    @Test
    void testDongDetail() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("gugunCode", gugunCode);
        map.put("name", dongName);
        log.info("===== 동 조회: " + dao.dongDetail(map));
    }


}
