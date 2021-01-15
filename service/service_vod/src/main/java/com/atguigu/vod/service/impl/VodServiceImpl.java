package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.Utils.ConstantVodUtils;
import com.atguigu.vod.Utils.InitVodCilent;
import com.atguigu.vod.service.VodService;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.text.CollatorUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-30 21:43
 **/

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) throws IOException {

        //获取文件名
        String filename = file.getOriginalFilename();
        //上传后显示名称
        String title = filename.substring(0, filename.lastIndexOf("."));
        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, filename, inputStream);

        UploadVideoImpl uploadVideo = new UploadVideoImpl();
        UploadStreamResponse response = uploadVideo.uploadStream(uploadStreamRequest);

        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            videoId = response.getVideoId();
        }
        return videoId;

    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {

        try {

            DefaultAcsClient initVodClient = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            String join = StringUtil.join(videoIdList.toArray(), ",");
            request.setVideoIds(join);
            initVodClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }
}
