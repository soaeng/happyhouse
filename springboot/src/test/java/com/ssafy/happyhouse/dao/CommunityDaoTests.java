package com.ssafy.happyhouse.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.happyhouse.dto.ReplyDto;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CommunityDaoTests {

	@Autowired
	CommunityDao dao;
	
	final int replyId = 3;
	final int userSeq = 4;
	
	// @Test
	void contextLoads() {
		assertNotNull(dao);
	}

	// @Test
	void testReplyInsertTest() {
		log.info(">>>>>>>>>>>>>>>>>>> 댓글 등록 test");
		ReplyDto dto = new ReplyDto();
		dto.setBoardId(1);
		dto.setText("테스트 댓글");
		dto.setUserSeq(userSeq);
		log.info("등록된 댓글 수: " + dao.replyInsert(dto));
	}

	// @Test
//	void testReplyDetailTest() {
//		log.info(">>>>>>>>>>>>>>>>>>> 댓글 조회 test");
//		log.info("조회한 댓글: " + dao.replyDetail(replyId));
//	}
	
	// @Test
	void testReplyDeleteTest() {
		log.info(">>>>>>>>>>>>>>>>>>> 댓글 삭제 test");
		log.info("삭제된 댓글 수: " + dao.replyDelete(replyId));
	}
	
	// @Test
	void testReplyUpdateTest() {
		log.info(">>>>>>>>>>>>>>>>>>> 댓글 수정 test");
		ReplyDto dto = new ReplyDto();
		dto.setReplyId(replyId);
		dto.setText("테스트 댓글 수정 ! !!");
		dto.setUserSeq(userSeq);
		log.info("수정된 댓글 수: " + dao.replyUpdate(dto));
	}
	
	@Test
	void testReplyListTest() {
		log.info(">>>>>>>>>>>>>>>>>>>> 댓글 목록 조회 test");
		int boardId = 1;
		List<ReplyDto> list = dao.replyList(boardId);
		list.forEach(reply -> log.info(reply));
	}
}
