package com.atguigu.staservice.service;

import com.atguigu.staservice.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-11
 */
public interface DailyService extends IService<Daily> {

    void countRegisterByDay(String day);

    Map<String, Object> showData(String type, String begin, String end);
}
