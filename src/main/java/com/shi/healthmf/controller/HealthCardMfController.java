package com.shi.healthmf.controller;

import com.shi.healthmf.bean.Result;
import com.shi.healthmf.bo.BindHealthCardMfBO;
import com.shi.healthmf.service.IHealthCardMfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "健康卡")
@RestController
@RequestMapping("hm/healthCardMf")
public class HealthCardMfController {

    @Autowired
    IHealthCardMfService iHealthCardMfService;

    @ApiOperation("绑定健康卡")
    @PostMapping("bind")
    public Result<Integer> bind(@RequestBody BindHealthCardMfBO bo){
        iHealthCardMfService.bind(bo);
        return Result.<Integer>builder().build().success();
    }

    @ApiOperation("获取服务地址")
    @GetMapping("url")
    public Result<String> url(){
        return Result.<String>builder().build().success().data(iHealthCardMfService.url());
    }

}
