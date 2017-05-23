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
                    String name = element.select("td.user > div > span > a").get(0).toString();

                    System.out.println();
                    System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--primary > span.subject")
                            .get(0).text());
                    System.out.println(element.select("td.date > div > time").get(0).attr("datetime"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
