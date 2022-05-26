package com.ssafy.happyhouse.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommunityFileDto {
	
	private int fileId;				// 파일 번호
	private int boardId;			// 게시글 번호
	private String fileName;		// 파일 이름
	private long fileSize;			// 파일 크기
	private String fileContentType;	// 파일 유형
	private String fileUrl;			// 파일 주소/uuid
	private LocalDateTime regDt;	// 파일 등록일
	
}