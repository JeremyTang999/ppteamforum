package com.ppteam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestController {

    @RequestMapping("/")
    public void test(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }
}
