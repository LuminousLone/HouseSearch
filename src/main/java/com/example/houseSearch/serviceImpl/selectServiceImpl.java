package com.example.houseSearch.serviceImpl;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.mapper.orderMapper;
import com.example.houseSearch.mapper.selectMapper;
import com.example.houseSearch.service.selectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class selectServiceImpl implements selectService {
    @Resource
    selectMapper selectMapper;

    @Override
    public ArrayList<House> searchByShape(String shape, String destination, Date today) {
        return selectMapper.getSearchByShape(shape, today, destination);
    }

    @Override
    public ArrayList<House> searchByPrice(int price, String destination, Date today) {
        return selectMapper.getSearchByPrice(price, today, destination);
    }

    @Override
    public ArrayList<House> getThisAll(Date today, String destination) {
        return selectMapper.getThisAll(today, destination);
    }

    @Override
    public ArrayList<House> getOddByDistance(Float distance, Date today, String destination) {
        return selectMapper.getOddByDistance(distance, today, destination);
    }
}
