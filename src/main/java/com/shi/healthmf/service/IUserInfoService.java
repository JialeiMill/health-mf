package com.shi.healthmf.service;

import com.shi.healthmf.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shi.healthmf.vo.WeChatServiceAccessTokenVO;
import com.shi.healthmf.vo.WeChatServiceUserInfoVO;

/**
* @author shijialei
* @description 针对表【user_info(用户信息)】的数据库操作Service
* @createDate 2024-09-11 16:54:54
*/
public interface IUserInfoService extends IService<UserInfo> {

    WeChatServiceUserInfoVO getUserInfo(String accessToken, String openid);

    WeChatServiceAccessTokenVO getAccessToken(String code);

    String login(String unionId);
}
