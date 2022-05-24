package com.ssafy.happyhouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="population")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Population {

	@Id
	@Column(length = 8)
	private String adstrdCode;
	
//	@Column
//	private double total_population;
	
	@Column
	private double totalLocal;
	
	@Column
	private double localMale;
	
	@Column
	private double localFemale;
	
//	@Column
//	private double total_forn;
}
