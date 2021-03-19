package com.atguigu.controller;

import com.atguigu.commonutils.R;
import com.atguigu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 13:58
 **/

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {


    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传路径
        String fileAvatar = ossService.uploadFileAvatar(file);
        return R.ok().data("url",fileAvatar);
    }
}

