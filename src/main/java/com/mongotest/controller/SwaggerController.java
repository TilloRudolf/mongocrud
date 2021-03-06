package com.mongotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SwaggerController {
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String root() {
        return "redirect:/swagger-ui.html";
    }
}
