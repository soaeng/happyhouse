package com.ssafy.happyhouse.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

        if (userProfileImageUrl == null || "null".equals(userProfileImageUrl) || "".equals(userProfileImageUrl)) {
            this.userProfileImageUrl = "/assets/images/faces/1.jpg";
        } else {
            this.userProfileImageUrl = userProfileImageUrl;
        }
    }

    public void setRegDt(Date regDt) {
        this.regDt = LocalDateTime.ofInstant(
                regDt.toInstant(), ZoneId.systemDefault()
        );
    }
}
