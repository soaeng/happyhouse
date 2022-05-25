package com.ssafy.happyhouse.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {
	
	private int userSeq;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userProfileImageUrl;
	private Date userRegisterDate;
	

	public void setUserProfileImageUrl(String userProfileImageUrl) {
		if( userProfileImageUrl == null || "null".equals(userProfileImageUrl) || "".equals(userProfileImageUrl)) {
			this.userProfileImageUrl = "/assets/images/faces/1.jpg";
		}else {
			this.userProfileImageUrl = userProfileImageUrl;
		}
	}
}
