package com.ssafy.happyhouse.dto;

import java.util.ArrayList;

import com.ssafy.happyhouse.entity.Population;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopulationListDto {
    ArrayList<Population> list = new ArrayList<>();
}
