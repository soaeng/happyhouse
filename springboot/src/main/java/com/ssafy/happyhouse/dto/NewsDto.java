package com.ssafy.happyhouse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDto {
    private String url;
    private String title;
    private String content;
    private String publish;
    private String date;
    private String img;
}
