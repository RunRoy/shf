package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lystart
 * @create 2023-04-26 18:27
 */
@Controller
public class IndexController {

    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";

    @RequestMapping("/")
    public String index() {
        return PAGE_INDEX;
    }

    @RequestMapping("/main")
    public String main() {
        return PAGE_MAIN;
    }
}
