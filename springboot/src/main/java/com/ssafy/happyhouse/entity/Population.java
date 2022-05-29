package com.ssafy.happyhouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "population")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Population {

    @Id
    @Column(length = 8)
    private String adstrdCode;

    @Column
    private double totalLocal;

    @Column
    private double totalMale;
    @Column
    private double maleTo19;
    @Column
    private double maleTo39;
    @Column
    private double maleTo59;
    @Column
    private double maleTo74;

    @Column
    private double totalFemale;
    @Column
    private double femaleTo19;
    @Column
    private double femaleTo39;
    @Column
    private double femaleTo59;
    @Column
    private double femaleTo74;

}
