package com.example.houseSearch.mapper;

import com.example.houseSearch.bean.LongLat;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface LongLatMapper {

    ArrayList<LongLat> getAll();


}
