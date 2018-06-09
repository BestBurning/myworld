package com.diyishuai.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(ModelMap map) {

        map.addAttribute("background_landscape_jpg", "https://ad-dashboard-static.oss-cn-beijing.aliyuncs.com/image-test/a3c7768a58f3472f9d5c583cd1e80d39.jpg");
        map.addAttribute("background_portrait_jpg", "https://ad-dashboard-static.oss-cn-beijing.aliyuncs.com/image-test/be40ce7b6f3a4a3988196f5961b057c2.jpg");
        map.addAttribute("ctx", "这是个按钮");
        return "preview" ;
    }
}