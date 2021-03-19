package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-11
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    //统计某一天注册人数,生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){

        dailyService.countRegisterByDay(day);
        return R.ok();
    }


    //图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){

        Map<String,Object> daily = dailyService.showData(type,begin,end);
        return R.ok().data(daily);
    }
}

