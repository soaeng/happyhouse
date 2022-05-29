package com.ssafy.happyhouse.dto;

import java.util.List;

import lombok.Data;

@Data
public class CommunityResultDto {

    private int result;
    private CommunityDto dto;
    private List<CommunityDto> list;
    private int count;

}
