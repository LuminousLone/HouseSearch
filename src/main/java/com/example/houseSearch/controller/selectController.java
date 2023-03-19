package com.example.houseSearch.controller;


import com.example.houseSearch.bean.House;
import com.example.houseSearch.bean.LongLat;
import com.example.houseSearch.service.orderService;
import com.example.houseSearch.service.searchService;
import com.example.houseSearch.service.selectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.PriorityQueue;

@Controller
public class selectController {

    final int[] Wt = new int[]{ 10,3,1};  // 权重


    @Resource
    searchService searchService;

    @Resource
    selectService  selectService;

    @Resource
    orderService orderService;



    @RequestMapping(value = "/selectByPrice",method = RequestMethod.GET)
    public ModelAndView selectPrice( @ModelAttribute("price") int price){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        ArrayList<House> result = selectService.searchByPrice(price,destination,date);
        ArrayList<LongLat> map = orderService.getAll();

        ModelAndView mv = new ModelAndView();


        mv.addObject("result",result);
        mv.addObject("map",map);
        mv.addObject("place",destination);

        mv.addObject("title","根据价格查询结果");
        if(result.isEmpty())
            mv.setViewName("empty");
        else
            mv.setViewName("order");

        return  mv;


    }



    @RequestMapping(value = "/selectByShape",method = RequestMethod.GET)
    public ModelAndView searchShape(@ModelAttribute("bedroom") String bedroom,@ModelAttribute("livingroom") String livingroom,@ModelAttribute("bathroom") String bathroom){


        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        String shape = bedroom + "室" + livingroom +"厅"+ bathroom +"卫";

        ArrayList<House> result = selectService.searchByShape(shape,destination,date);
        ArrayList<LongLat> map = orderService.getAll();

        ModelAndView mv = new ModelAndView();

        mv.addObject("result",result);
        mv.addObject("map",map);
        mv.addObject("place",destination);

        mv.addObject("title","根据房型查询结果");

        if(result.isEmpty())
            mv.setViewName("empty");
        else
            mv.setViewName("order");

       return mv;

    }

    @RequestMapping(value = "/evaluate",method = RequestMethod.GET)
    public ModelAndView evaluateHouse(){

        String destination = searchService.getWhere();
        Date date = searchService.getDate();

        // 拿到此次搜索出的所有数据,输入
        ArrayList<House> this_all = selectService.getThisAll(date,destination);



        float[][] input = new float[this_all.size()][3];

        float sum_dis = 0, sum_high=0;
        // [价格/面积，距离，楼高]
        for( int i=0;i<this_all.size();i++){

            input[i][0] = this_all.get(i).getPrice() / this_all.get(i).getSize();

            input[i][1] = (float) this_all.get(i).getDistance();

            sum_dis += (float) this_all.get(i).getDistance();

            input[i][2] = this_all.get(i).getHeight();

            sum_high +=  this_all.get(i).getHeight();

        }

        float avg_dis = sum_dis / this_all.size();

        int avg_high = (int) (sum_high / this_all.size());


        int[][] hidden = new int[input.length][3];

        for(int i=0;i<hidden.length;i++){

            // 根据距离得到以前
            ArrayList<House> odd = selectService.getOddByDistance(input[i][1],date,destination);


            // 计算平均价格/面积
            float avg_price, sum_price_size=0;

            if(odd.isEmpty())
                avg_price =  input[i][0];

             else {
                for (House house : odd)
                    sum_price_size += house.getPrice() / house.getSize();

                    avg_price = sum_price_size / odd.size();
            }

            if( input[i][0] <= avg_price)
                hidden[i][0] = 5;
            else
                hidden[i][0] = 0;


            // 距离
            if( input[i][1] <= avg_dis)
                hidden[i][1] = 2;
            else
                hidden[i][1] = 1;

            // 楼高
            if(input[i][2] <= avg_high)
                hidden[i][2] = 1;
            else
                hidden[i][2] = 0;


        }

        // 根据权重得到输出
        int[] output = new int[hidden.length];


        int sum = 0;
        for(int i=0;i<output.length;i++) {
            output[i] = hidden[i][0] * Wt[0] + hidden[i][1] * Wt[1] + hidden[i][2] * Wt[2];
            sum += output[i];
        }

        // 计算概率
        float[] out_pos = new float[output.length];
     //   HashMap<Integer,Float> hashMap = new HashMap<>();

        PriorityQueue<float[]> priorityQueue = new PriorityQueue<>(
                (float[] pair1,float[] pair2)->{

                    if( pair2[1] > pair1[1])
                        return 1;
                    else if( pair2[1] == pair1[1])
                        return 0;
                    else return -1;

                }
        );

        for(int i=0;i<out_pos.length;i++){
            out_pos[i] =(float) output[i] / sum;
          //  hashMap.put(i,out_pos[i]);
            priorityQueue.add(new float[]{i,out_pos[i]});
        }

       ModelAndView mv = new ModelAndView();

       ArrayList<House> result = new ArrayList<>();

       ArrayList<Float> Poss = new ArrayList<>();

       ArrayList<String> star = new ArrayList<>();



        while ( !priorityQueue.isEmpty()){
            float[] pair = priorityQueue.poll();

            int i= (int) pair[0];

            result.add(this_all.get(i));

            Poss.add(pair[1]);

            if(pair[1] >= 0.8 )
                star.add("5星推荐！");
            else if( pair[1] >= 0.6 && pair[1] < 0.8)
                star.add("4星推荐！");
            else if( pair[1] >= 0.4 && pair[1] <0.6)
                star.add("3星推荐！");
            else if( pair[1] >= 0.2 && pair[1] < 0.4)
                star.add("2星推荐！");
            else
               star.add("1星推荐！");


        }

        ArrayList<LongLat> map = orderService.getAll();

        mv.addObject("map",map);

        mv.addObject("place",destination);

        mv.addObject("result",result);

        mv.addObject("Poss",Poss);

        mv.addObject("star",star);

        mv.setViewName("evaluate");

        return mv;





    }

}
