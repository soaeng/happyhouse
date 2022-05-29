package com.ssafy.happyhouse.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.ssafy.happyhouse.dto.AreaCodeDto;
import com.ssafy.happyhouse.dto.NewsDto;


@Component
public class JsoupComponent {

    public List<NewsDto> getNewsList(AreaCodeDto dto) {
        String url = "";
        if (dto == null) url = "https://land.naver.com/news/headline.naver";
        else url = "https://land.naver.com/news/region.naver?city_no="
                + dto.getCityCode() + "00000000&dvsn_no=" + dto.getGugunCode() + "00000";
        Connection conn = Jsoup.connect(url);
        try {
            Document doc = conn.get();
            return getNewsList(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NewsDto> getNewsList(Document document) {
        Elements dl = document.select(".section_headline .headline_list li dl");
        List<NewsDto> list = new ArrayList<>();

        for (Element element : dl) {
            list.add(createNewsDto(element));
        }
        for (NewsDto dto : list) {
            System.out.println(dto);
        }
        return list;
    }

    public NewsDto createNewsDto(Element dl) {
        Elements dt = dl.select("dt");
        Elements dd = dl.select("dd");

        NewsDto dto = NewsDto.builder().build();

        String url = "https://land.naver.com/" + dt.select("a").attr("href");
        String title = dt.select("a").text();
        String content = dd.html();
        content = content.split("<span ")[0];
        String publish = dd.select("span.writing").text();
        String date = dd.select("span.date").text();
        String img = dt.select("a img").attr("src");

        dto.setUrl(url);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPublish(publish);
        dto.setDate(date);
        dto.setImg(img);

        return dto;
    }
}
