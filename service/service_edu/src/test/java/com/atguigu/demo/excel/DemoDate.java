package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 16:59
 **/
@Data
public class DemoDate {

    @ExcelProperty(value = "学生编号",index = 0)
    private Integer mno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String mname;
}
