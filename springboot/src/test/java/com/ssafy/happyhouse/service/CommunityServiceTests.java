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
public class CommunityServiceTests {

    final int userSeq = 1;

    @Autowired
    private CommunityService service;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }


    // 시도코드
    // @Test
    void testSidoList() {
        log.info("===== 게시글 삭제 테스트 ====");
        service.communityDelete(2);
    }

}
