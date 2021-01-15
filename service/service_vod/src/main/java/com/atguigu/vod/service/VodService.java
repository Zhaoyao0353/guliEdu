package com.atguigu.vod.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface VodService {
    String uploadVideoAly(MultipartFile file) throws IOException;

    void removeMoreAlyVideo(List<String> videoIdList);
}
