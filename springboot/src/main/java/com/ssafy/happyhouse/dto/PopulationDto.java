package com.ssafy.happyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PopulationDto {

	private String adstrdCode;
	private double totalLocal;

	private double totalMale;
	private double maleTo19;
	private double maleTo39;
	private double maleTo59;
	private double maleTo74;
	
	private double totalFemale;
	private double femaleTo19;
	private double femaleTo39;
	private double femaleTo59;
	private double femaleTo74;
}
