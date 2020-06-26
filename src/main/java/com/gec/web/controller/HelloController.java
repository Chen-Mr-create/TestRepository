package com.gec.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/findAllUsers") //针对get请求方式的注解
    public ModelAndView findAllUsers(){
        ModelAndView mv = new ModelAndView();
        List<String> list = new ArrayList<>();
        list.add("Mike");
        list.add("jack");
        list.add("chen");
        list.add("zheng");
        mv.addObject("names",list);
        mv.setViewName("index");
        return  mv;
    }
}
