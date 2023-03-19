package com.example.houseSearch.serviceImpl;

import com.example.houseSearch.bean.House;
import com.example.houseSearch.bean.LongLat;
import com.example.houseSearch.mapper.LongLatMapper;
import com.example.houseSearch.mapper.orderMapper;
import com.example.houseSearch.service.orderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class orderServiceImpl implements orderService {

    @Resource
    orderMapper orderMapper;

    @Resource
    LongLatMapper longLatMapper;



    @Override
    public ArrayList<House> orderByPrice(String flag, String destination, Date today) {

        if (flag.equals("bigger"))
            return orderMapper.getOrderByPriceBig(today,destination);
        else
            return orderMapper.getOrderByPriceSmall(today,destination);



    }

    @Override
    public ArrayList<House> orderByDistance(String flag, String destination, Date today) {
        if( flag.equals("bigger"))
            return orderMapper.getOrderByDistanceBig(today, destination);
        else
            return orderMapper.getOrderByDistanceSmall(today, destination);


    }

    @Override
    public ArrayList<House> orderBySize(String flag, String destination, Date today) {
        if( flag.equals("bigger"))
            return orderMapper.getOrderBySizeBig(today, destination);
        else
            return orderMapper.getOrderBySizeSmall(today, destination);
    }

    @Override
    public ArrayList<LongLat> getAll() {
        return longLatMapper.getAll();
    }

//    @Override
//    public ArrayList<House> searchByShape(String shape,String destination, Date today) {
//        return orderMapper.getSearchByShape(shape,today, destination);
//    }
//
//    @Override
//    public ArrayList<House> searchByPrice(int price, String destination, Date today) {
//
//        return orderMapper.getSearchByPrice(price, today, destination);
//    }


}
