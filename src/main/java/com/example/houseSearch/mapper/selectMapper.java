package com.example.houseSearch.mapper;

import com.example.houseSearch.bean.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.ArrayList;

@Mapper
public interface selectMapper {

    ArrayList<House> getSearchByShape(@Param("shape") String shape , @Param("today") Date today, @Param("destination") String destination);

    ArrayList<House> getSearchByPrice(@Param("price") int price, @Param("today") Date today, @Param("destination")  String destination );

    // 此次所有搜索结果
    ArrayList<House> getThisAll(@Param("today") Date today,@Param("destination") String destination);

    // 找到 以前 所有距离相同的house
    ArrayList<House> getOddByDistance( @Param("distance") Float distance,@Param("today") Date today,@Param("destination") String destination  );
}
