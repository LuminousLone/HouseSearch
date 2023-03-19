package com.example.houseSearch;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.mapper.orderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;


@SpringBootTest
class HouseSearchApplicationTests {

    @Resource
    orderMapper orderMapper;

    @Test
    void contextLoads() {
        // 转SQL的时间
        Date date = new Date();
        long timeInMilliSeconds = date.getTime();
        java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);

        ArrayList<House> houses = orderMapper.getOrderByPriceBig(date1,"民族大学");

        System.out.println(houses.get(0).toString());
    }

//    void

}
