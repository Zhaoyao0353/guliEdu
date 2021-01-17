package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2021-01-17 20:24
 **/
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前八课程前四名师
    @GetMapping("index")
    public R index(){
        QueryWrapper<EduCourse> course = new QueryWrapper<>();
        course.orderByDesc("id");
        course.last("limit 8");
        List<EduCourse> eduList = courseService.list(course);

        QueryWrapper<EduTeacher> teacher = new QueryWrapper<>();
        teacher.orderByDesc("id");
        teacher.last("limit 4");
        List<EduTeacher> teachers = teacherService.list(teacher);
        return R.ok().data("eduList",eduList).data("teacherList",teachers);
    }
}
