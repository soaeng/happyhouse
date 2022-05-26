package com.ssafy.happyhouse.dto;

import lombok.Data;

@Data
public class CommunityParamDto {
	
	private int limit;			// 가져올 게시글 수
	private int offset;			// 앞에서 생략할 데이터 수
	private String type;		// 검색 종류
	private String keyword;		// 검색 키워드
	
	private int boardId;		// 게시글 번호
	private int replyId;		// 댓글 번호
	private int userSeq;		// 작성자
	
}
