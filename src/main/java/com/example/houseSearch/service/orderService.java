package com.example.houseSearch.service;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.bean.LongLat;

import java.sql.Date;
import java.util.ArrayList;

public interface orderService {

    ArrayList<House> orderByPrice(String flag, String destination, Date today);

    ArrayList<House> orderByDistance(String flag, String destination, Date today);

    ArrayList<House> orderBySize(String flag, String destination, Date today);

    ArrayList<LongLat> getAll();

//    ArrayList<House> searchByShape(String shape,String destination, Date today);
//
//    ArrayList<House> searchByPrice(int price,String destination, Date today);
}
