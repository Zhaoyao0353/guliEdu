package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2021-01-02 13:59
 **/
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
