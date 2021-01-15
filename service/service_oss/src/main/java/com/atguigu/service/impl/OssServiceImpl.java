package com.atguigu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.service.OssService;
import com.atguigu.util.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 13:48
 **/
@Service
public class OssServiceImpl implements OssService {



    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endPoind = ConstantPropertiesUtil.END_POIND;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;


        try {
            //创建oss实例
            OSS build = new OSSClientBuilder().build(endPoind, accessKeyId, accessKeySecret);

            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();

            String s = UUID.randomUUID().toString().replaceAll("-", "");
            filename =  s + filename;

            String time = new DateTime().toString("yyyy/MM/dd");

            filename = time + "/" + filename;

            build.putObject(bucketName,filename,inputStream);

            build.shutdown();
            return "https://"+bucketName+"."+endPoind+"/"+filename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
