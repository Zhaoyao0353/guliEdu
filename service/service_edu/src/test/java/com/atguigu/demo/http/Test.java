package com.atguigu.demo.http;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 23:54
 **/
public class Test {
    public static void main(String[] args) {

        //发送 GET 请求
        String str1 = BackEndHttpRequest.sendGet("http://localhost:8080/showWaterregion", "page=1&rows=5&regionID=4");
        System.out.println(str1);

        //发送 POST 请求
        String str2 = BackEndHttpRequest.sendPost("http://localhost:8080/waterregion/flow/year", "type=1&regionID=2");
        System.out.println(str2);
    }
}
