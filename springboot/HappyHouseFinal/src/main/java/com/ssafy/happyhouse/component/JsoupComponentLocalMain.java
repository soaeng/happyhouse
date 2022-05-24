package com.ssafy.happyhouse.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ssafy.happyhouse.dto.NewsDto;

public class JsoupComponentLocalMain {
	public static void getNewsList() {
		final String url = "https://land.naver.com/news/headline.naver";
		Connection conn = Jsoup.connect(url);
		
		try {
			Document doc = conn.get();
			String title = getNewsTitle(doc);
			String address = getNewsUrl(doc);
			
			System.out.println(title);
			System.out.println(address);
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getNewsTitle(Document document) {
		Elements dl = document.select(".section_headline .headline_list li dl");
		//Elements nl = document.select(".spot_headline .news_area .group .news_list");
		
		StringBuilder sb = new StringBuilder();

//		for(Element element: nl) {
//			Element dt = element.child(0).child(0);
//			sb.append(dt.text()).append(" ");
//			break;
//		}
		
		for(Element element: dl) {
			Element dt = element.child(1).child(0);
			sb.append(dt.text()).append(" ");
			break;
		}
		
		return sb.toString();
	}
	
	public static String getNewsUrl(Document document) {
		Elements dldt = document.select(".section_headline .headline_list li dl dt:nth-child(2)");
		// Elements nldt = document.select(".section_headline .headline_list li dl dt");
		String url = null;
		if(dldt != null) url = "https://land.naver.com/" + dldt.select("a").attr("href");
		// else if(nldt != null) url = "https://land.naver.com/" + nldt.select("a").attr("href");
		return url;
	}
	
}
