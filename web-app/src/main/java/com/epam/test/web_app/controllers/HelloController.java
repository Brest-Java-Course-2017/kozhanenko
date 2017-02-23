package com.epam.test.web_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by andrei on 24.2.17.
 */
@Controller
public class HelloController {

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:hello";
    }

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}