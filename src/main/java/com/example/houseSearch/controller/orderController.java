package com.example.houseSearch.controller;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.bean.LongLat;
import com.example.houseSearch.service.orderService;
import com.example.houseSearch.service.searchService;
import com.example.houseSearch.serviceImpl.orderServiceImpl;
import com.example.houseSearch.serviceImpl.searchServiceImpl;
import com.example.houseSearch.service.searchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class orderController {

    @Resource
    orderService orderService;
  //  orderServiceImpl orderService;

    @Resource
     searchService searchService;
  //  searchServiceImpl searchService ;

    @RequestMapping(value = "/orderByPrice",method = RequestMethod.GET)
    public ModelAndView orderPrice(@ModelAttribute("select") String flag){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderByPrice(flag,destination,date);
        ArrayList<LongLat> map = orderService.getAll();


        ModelAndView mv = new ModelAndView();
        mv.addObject("result",result);
        mv.addObject("map",map);
        mv.addObject("place",destination);

        mv.addObject("title","按照价格排序结果");
        mv.setViewName("order");

        return mv;


    }

    @ResponseBody
    @RequestMapping(value = "/orderByDistance",method = RequestMethod.GET)
    public ModelAndView orderDistance( @ModelAttribute("select") String flag){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderByDistance(flag,destination,date);
        ArrayList<LongLat> map = orderService.getAll();



        ModelAndView mv = new ModelAndView();
        mv.addObject("result",result);
        mv.addObject("map",map);
        mv.addObject("place",destination);
        mv.addObject("title","按照距离排序结果");
        mv.setViewName("order");
        return mv;


    }

    @ResponseBody
    @RequestMapping(value = "/orderBySize",method = RequestMethod.GET)
    public ModelAndView orderSize( @ModelAttribute("select") String flag){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();


        ArrayList<House> result = orderService.orderBySize(flag,destination,date);
        ArrayList<LongLat> map = orderService.getAll();

        ModelAndView mv = new ModelAndView();
        mv.addObject("result",result);
        mv.addObject("map",map);
        mv.addObject("place",destination);
        mv.addObject("title","按照面积排序结果");
        mv.setViewName("order");

        return mv;


    }





}
