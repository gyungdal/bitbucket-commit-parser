package com.gyungdal;

import com.sun.net.ssl.HttpsURLConnection;
import jdk.nashorn.internal.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class Main {
    private int page;
    Main(){
        page = 1;
    }
    public static void main(String[] args) {
        new Main().commit("https://bitbucket.org/nenohidayo/nenohidayo/commits/", "master");
    }

    public void branch(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .get();
            //ALL BRANCH PARSING
            String branchUrl = "https://bitbucket.org" + doc.select("button.aui-button.branch-dialog-trigger").get(0).attr("data-branches-tags-url");
            System.out.println(branchUrl);
            String input = Jsoup.connect(branchUrl)
                    .ignoreContentType(true)
                    .get().toString();
            String[] temp = input.split(",");
            for(String test : temp){
                if(test.contains("name")){
                    System.out.println(test.split("\"")[3]);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void commit(String URL, String commit){
        try {
            if(URL.charAt(URL.length() - 1) == '/')
                URL = URL.substring(0, URL.length() - 1);
            String url = URL
                     + ((!commit.isEmpty() ? "/branch/" + commit : "") + "?page=" + page);
            System.out.println(url);
            Document doc = Jsoup.connect(url)
                    .get();

            //COMMIT PARSING
            Elements elements = doc.select(".iterable-item");
            if(elements.isEmpty()){
                System.err.println("WHAT????");
                return;
            }
            for(Element element : elements) {
                System.out.println("==============================");
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
                    System.out.println("master");
                }else{
                    System.out.println(element.select("td.text.flex-content--column > div > div.flex-content--secondary > div > div > dl > dd > a").get(0).text());
                }
                System.out.println("==============================");
            }
            if(elements.toString().contains("page=" + (++page)))
                commit(URL, commit);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
