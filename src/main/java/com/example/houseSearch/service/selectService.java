package com.example.houseSearch.service;

import com.example.houseSearch.bean.House;

import java.sql.Date;
import java.util.ArrayList;

public interface selectService {
    ArrayList<House> searchByShape(String shape, String destination, Date today);

    ArrayList<House> searchByPrice(int price,String destination, Date today);

    ArrayList<House> getThisAll(Date today, String destination);

    ArrayList<House> getOddByDistance(Float distance,Date today,String destination);


}
