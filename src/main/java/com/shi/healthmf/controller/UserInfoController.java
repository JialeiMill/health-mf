package com.shi.healthmf.controller;

import com.shi.healthmf.bean.Result;
import com.shi.healthmf.service.IUserInfoService;
import com.shi.healthmf.vo.WeChatServiceAccessTokenVO;
import com.shi.healthmf.vo.WeChatServiceUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
@RequestMapping("hm/userInfo")
public class UserInfoController {

    @Autowired
    IUserInfoService iUserInfoService;

    @ApiOperation("获取授权Token")
    @GetMapping("getAccessToken")
    public Result<WeChatServiceAccessTokenVO> getAccessToken(@ApiParam("授权码") @RequestParam String code){
        return Result.<WeChatServiceAccessTokenVO>builder().build().success().data(iUserInfoService.getAccessToken(code));
    }


    @ApiOperation("获取用户信息")
    @GetMapping("getUserInfo")
    public Result<WeChatServiceUserInfoVO> getUserInfo(@ApiParam("授权Token") @RequestParam String accessToken,
                                                       @ApiParam("OpenID") @RequestParam String openid){
        return Result.<WeChatServiceUserInfoVO>builder().build().success().data(iUserInfoService.getUserInfo(accessToken, openid));
    }

    @ApiOperation("登录")
    @GetMapping("login")
    public Result<String> login(@ApiParam("unionId") @RequestParam String unionId){
        return Result.<String>builder().build().success().data(iUserInfoService.login(unionId));
    }

}
