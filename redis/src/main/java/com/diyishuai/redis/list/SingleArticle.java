package com.diyishuai.redis.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 访问单个新闻页：
 * http://www.huxiu.com/article/102062/1.html 需要：标题，内容
 */
public class SingleArticle {

    public static void main(String[] args) throws Exception {
        // 第一步：访问页面
        String url = "http://www.huxiu.com/article/102062/1.html";
        Document document = Jsoup.connect(url).get();

        // 第二步：解析页面
        Elements titleElements = document.getElementsByTag("title");
        String title = titleElements.get(0).text();

        Elements elements = document.select("div #article_content");
        String content = elements.text();

        // 第三步：打印
        System.out.println("title:" + title);
        System.out.println("content:" + content);
    }

}
