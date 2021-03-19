package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2021-02-19 08:50
 **/


//@CrossOrigin
@Controller //只请求地址 不返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("callback")
    public String callback(String code, String state) throws Exception {
        try {

            //1 获取code值 临时票据 验证码
            // 2 用code请求wx固定地址 得到access_id和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //3 拼接秘钥id和code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            // 请求拼接好的地址 得到access_id和openid
            // 利用http请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);


            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) hashMap.get("access_token");
            String openid = (String) hashMap.get("openid");


            //扫描人信息添加数据库
            //openid验证
            UcenterMember openIdMember = ucenterMemberService.getOpenIdMember(openid);
            if (openIdMember == null) {

                //用access_token和openid获取信息
                //访问微信资源服务器
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                //发送请求
                String userinfourl = HttpClientUtils.get(userInfoUrl);
                HashMap map = gson.fromJson(userinfourl, HashMap.class);
                String nickname = (String) map.get("nickname");
                String headimgurl = (String) map.get("headimgurl");

                openIdMember = new UcenterMember();
                openIdMember.setOpenid(openid);
                openIdMember.setNickname(nickname);
                openIdMember.setAvatar(headimgurl);
                ucenterMemberService.save(openIdMember);
            }

            //使用jwt将member对象生成字符串
            String jwtToken = JwtUtils.getJwtToken(openIdMember.getId(), openIdMember.getNickname());

            //返回首页
            return "redirect:http://localhost:3000?token=" + jwtToken;

        } catch (Exception e) {
            throw new GuliException(20001, "登录失败");
        }
    }

    //生成二维码
    @GetMapping("login")
    public String getWXCode() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );

        //重定向到请求微信地址里面
        return "redirect:"+url;
    }
}
