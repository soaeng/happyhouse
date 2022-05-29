package com.ssafy.happyhouse.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.NewsDto;
import com.ssafy.happyhouse.service.CrawlingServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(
        origins = "http://localhost:5500",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS}
)
@RequiredArgsConstructor
@Log4j2
public class CrawlingController {
    private final CrawlingServiceImpl service;

    @GetMapping("/craw/news")
    public List<NewsDto> getNewsList(HttpServletRequest request) {
        return service.getNewsList();
    }

    @GetMapping("/craw/news/area")
    public List<NewsDto> getAreaNewsList(AreaCodeDto dto, HttpServletRequest request) {
        log.info(dto);
        return service.getNewsList(dto);

    }
}
