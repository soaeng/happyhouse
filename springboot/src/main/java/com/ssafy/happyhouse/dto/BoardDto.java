package com.ssafy.happyhouse.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class BoardDto {
	
	private int boardId;
	private int userSeq;
	private String userName;
	private String userProfileImageUrl;
	private String title;
	private String content;
	private LocalDateTime regDt;
	private int readCount;

	private boolean sameUser;
	
	private List<BoardFileDto> fileList;


	public void setUserProfileImageUrl(String userProfileImageUrl) {
		
		if( userProfileImageUrl == null || "null".equals(userProfileImageUrl) || "".equals(userProfileImageUrl)) {
			this.userProfileImageUrl = "/img/noProfile.png";
		}else {
			this.userProfileImageUrl = userProfileImageUrl;
		}
	}

	public void setRegDt(Date regDt) {
		this.regDt = LocalDateTime.ofInstant(
				regDt.toInstant(), ZoneId.systemDefault()
		);	// for Mybatis Date Mapping
	}
}
