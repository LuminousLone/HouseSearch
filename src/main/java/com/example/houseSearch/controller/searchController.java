package com.example.houseSearch.controller;

import com.example.houseSearch.service.searchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller

public class searchController {

    @Resource
    searchService service;

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String searchMethod(@ModelAttribute("search") String where, @ModelAttribute("select") String city){


       service.search(where,city);


        return "result";

    }

    @RequestMapping("/back")
    public String goBack(){

        return "search";
    }

    @RequestMapping("/goIndex")
    public String goIndex(){
        return "index_";
    }


}
