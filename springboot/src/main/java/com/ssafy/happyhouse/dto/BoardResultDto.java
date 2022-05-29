package com.ssafy.happyhouse.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardResultDto {

    private int result;
    private BoardDto dto;
    private List<BoardDto> list;
    private int count;

}
