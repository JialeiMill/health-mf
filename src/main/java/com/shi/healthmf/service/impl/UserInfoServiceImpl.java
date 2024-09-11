package com.shi.healthmf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shi.healthmf.config.properties.WeChatServiceProperties;
import com.shi.healthmf.entity.UserInfo;
import com.shi.healthmf.excs.BusinessException;
import com.shi.healthmf.service.IUserInfoService;
import com.shi.healthmf.mapper.UserInfoMapper;
import com.shi.healthmf.utils.JwtUtil;
import com.shi.healthmf.vo.WeChatServiceAccessTokenVO;
import com.shi.healthmf.vo.WeChatServiceUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
* @author shijialei
* @description 针对表【user_info(用户信息)】的数据库操作Service实现
* @createDate 2024-09-11 16:54:54
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements IUserInfoService{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WeChatServiceProperties weChatServiceProperties;

    @Override
    public WeChatServiceUserInfoVO getUserInfo(String accessToken, String openid) {
        // 获取请求地址
        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", accessToken, openid);
        // 发送请求
        ResponseEntity<WeChatServiceUserInfoVO> responseEntity = restTemplate.getForEntity(url, WeChatServiceUserInfoVO.class);
        // 判断是否成功
        if (HttpStatus.OK == responseEntity.getStatusCode()){
            return responseEntity.getBody();
        }
        else {
            throw new BusinessException("微信服务异常");
        }
    }

    @Override
    public WeChatServiceAccessTokenVO getAccessToken(String code) {
        // 获取请求地址
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", weChatServiceProperties.getAppId(), weChatServiceProperties.getAppSecret(), code);
        // 发送请求
        ResponseEntity<WeChatServiceAccessTokenVO> responseEntity = restTemplate.getForEntity(url, WeChatServiceAccessTokenVO.class);
        // 判断是否成功
        if (HttpStatus.OK == responseEntity.getStatusCode()){
            return responseEntity.getBody();
        }
        else {
            throw new BusinessException("微信服务异常");
        }
    }

    @Override
    public String login(String unionId) {
        UserInfo userInfo = getOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUnionId, unionId));
        if (userInfo == null){
            userInfo = new UserInfo();
            userInfo.setUnionId(unionId);
            save(userInfo);
        }
        Map<String, String> param = new HashMap<>();
        param.put("id", userInfo.getId().toString());
        return JwtUtil.encode(param);
    }
}




