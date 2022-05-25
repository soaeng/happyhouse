package com.ssafy.happyhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.happyhouse.entity.Population;

public interface PopulationRepository extends JpaRepository<Population, String> {
}
