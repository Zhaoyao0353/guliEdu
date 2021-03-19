package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2021-01-24 00:52
 **/
public interface MsmService {

    boolean send(Map<String, Object> param, String phone);
}
