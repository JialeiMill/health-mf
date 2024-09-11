package com.shi.healthmf.service;

import com.shi.healthmf.bo.BindHealthCardMfBO;
import com.shi.healthmf.entity.HealthCardMf;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author shijialei
* @description 针对表【health_card_mf(健康卡-觅方)】的数据库操作Service
* @createDate 2024-09-11 16:41:47
*/
public interface IHealthCardMfService extends IService<HealthCardMf> {

    void bind(BindHealthCardMfBO bo);

    String url();
}
