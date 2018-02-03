package com.diyishuai.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static sun.net.www.protocol.http.HttpURLConnection.userAgent;

public class Crawler implements Runnable{
    public static final String domain = "https://www.lagou.com/jobs";


    public void run() {
        Connection conn = Jsoup.connect(domain);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", userAgent);
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
