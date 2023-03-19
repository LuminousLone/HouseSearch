package com.example.houseSearch.serviceImpl;
import com.example.houseSearch.service.searchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;

@Service
public class searchServiceImpl implements searchService {

    // 保存已经搜索过的位置
    HashSet<String> visited ;

    String now ;  // 当前正在搜索的位置
    Date  today;

    public searchServiceImpl(){

        visited = new HashSet<>();

    }


    @Override
    public void search(String where,String city) {

        this.now = where;
        this.today = new Date( new java.util.Date().getTime());

        StringBuilder rs = new StringBuilder("");
        rs.append(where).append("+");
        if(!city.equals("nothing")) rs.append(city);

        if( !this.visited.contains(where)){

        //    String[] arg = new String[]{"python","D:\\HouseSearch\\pythonFile\\get.py",rs.toString()};
            String[] arg = new String[]{"python","pythonFile\\get.py",rs.toString()};

        //     调用python程序
            try {
                Process pr = Runtime.getRuntime().exec(arg);

            } catch (IOException e) {
                e.printStackTrace();
            }


            this.visited.add(where);

        }

            // 本次 已经搜索过 直接去数据库检索

    }

    @Override
    public String getWhere() {
        return this.getNow();
    }

    @Override
    public Date getDate() {
       return this.getToday();
    }

    public String getNow() {
        return now;
    }

    public Date getToday(){
        return today;
    }
}
