package com.gyungdal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
                Document doc = Jsoup.connect("https://bitbucket.org/GyungDal/dormitory/commits")
                    .get();
            Elements elements = doc.select(".iterable-item");
            for(Element element : elements) {
                System.out.println("==============================");
                System.out.println(element.attr("id"));
                if (!element.toString().contains("unmapped-user")){
                    System.out.println(element.select("td.user > div > span > span > a").get(0).attr("title"));
                    System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--primary > span.subject")
                            .get(0).text());
                    if(element.toString().contains("body")){
                        System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--primary > span.body")
                                .get(0).text());
                    }
                    System.out.println(element.select("td.date > div > time").get(0).attr("datetime"));
                } else {
                    if(!element.select("span.unmapped-user > span:nth-child(2)").isEmpty()) {
                        String name = element.select("span.unmapped-user > span:nth-child(2)").get(0).text();
                        System.out.println(name);
                    }else{
                        System.out.println(element.toString());
                    }
                    System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--primary > span.subject")
                            .get(0).text());
                    System.out.println(element.select("td.date > div > time").get(0).attr("datetime"));
                }
                if(element.select("td.text.flex-content--column > div > div.flex-content--secondary > div > div > dl > dd > a").isEmpty()){
                    System.out.println("브랜치 없음");
                }else{
                    System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--secondary > div > div > dl > dd > a").get(0).text());
                }
                System.out.println("==============================");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
