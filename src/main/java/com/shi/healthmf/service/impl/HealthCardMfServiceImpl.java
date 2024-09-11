package com.shi.healthmf.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shi.healthmf.bo.BindHealthCardMfBO;
import com.shi.healthmf.config.properties.HealthCardMfProperties;
import com.shi.healthmf.entity.HealthCardMf;
import com.shi.healthmf.entity.UserInfo;
import com.shi.healthmf.excs.BusinessException;
import com.shi.healthmf.excs.NotLoginException;
import com.shi.healthmf.service.IHealthCardMfService;
import com.shi.healthmf.mapper.HealthCardMfMapper;
import com.shi.healthmf.service.IUserInfoService;
import com.shi.healthmf.utils.JwtUtil;
import com.shi.healthmf.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
* @author shijialei
* @description 针对表【health_card_mf(健康卡-觅方)】的数据库操作Service实现
* @createDate 2024-09-11 16:41:47
*/
@Slf4j
@Service
public class HealthCardMfServiceImpl extends ServiceImpl<HealthCardMfMapper, HealthCardMf>
    implements IHealthCardMfService{

    @Autowired
    HttpServletRequest request;
    @Autowired
    IUserInfoService iUserInfoService;
    @Autowired
    HealthCardMfProperties healthCardMfProperties;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void bind(BindHealthCardMfBO bo) {
        Long userId = getUserId();

        HealthCardMf card = getOne(new LambdaQueryWrapper<HealthCardMf>().eq(HealthCardMf::getCardPass, bo.getCardPass()));
        if (card == null){
            throw new BusinessException("激活码错误");
        }
        if (card.getStatus() == 2){
            throw new BusinessException("激活码已使用");
        }
        open(bo);
        card.setStatus(2);
        card.setBindUserId(userId);
        card.setBindTime(LocalDateTime.now());
        card.setBindPhone(bo.getMobile());
        updateById(card);
        UserInfo userInfo = iUserInfoService.getById(userId);
        userInfo.setRealName(bo.getRealName());
        userInfo.setIdCard(bo.getIdCardNumber());
        userInfo.setRealTime(LocalDateTime.now());
        iUserInfoService.updateById(userInfo);
    }

    @Override
    public String url() {
        Long userId = getUserId();
        HealthCardMf card = getOne(new LambdaQueryWrapper<HealthCardMf>().eq(HealthCardMf::getBindUserId, userId));
        String fwUrl = null;
        try {
            log.info("健康卡Url:{}", card.getBindPhone());
            Map<String, String> headers = headers();
            HttpEntity<JSONObject> entity = RestTemplateUtil.generateEntity(null, headers);
            String url = String.format("https://api.dyhz.com/platform/service365/url?telephone=%s", card.getBindPhone());
            log.info("请求信息:{}|{}", url, headers);
            ResponseEntity<JSONObject> resEntity = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
            JSONObject body = resEntity.getBody();
            log.info("响应信息:{}", body);
            fwUrl = body.getJSONObject("data").getString("url");
        } catch (Exception e) {
            log.error("获取健康服务URL-异常:{}", card.getBindPhone(), e);
        }
        if (StringUtils.isBlank(fwUrl)){
            fwUrl = healthCardMfProperties.getBakFwUrl();
        }
        return fwUrl;
    }

    public Long getUserId(){
        String tk = request.getHeader("Authorization");
        if (StringUtils.isBlank(tk)){
            throw new NotLoginException();
        }
        try {
            Map<String, Claim> decode = JwtUtil.decode(tk);
            return Long.parseLong(decode.get("id").toString());
        } catch (Exception e) {
            throw new NotLoginException();
        }
    }

    private void open(BindHealthCardMfBO bo) {
        try {
            log.info("健康卡开卡:{}", bo);
            Map<String, String> headers = headers();
            HttpEntity<JSONObject> entity = RestTemplateUtil.generateEntity(null, headers);
            String url = String.format("https://api.dyhz.com/platform/service365?name=%s&idNumber=%s&telephone=%s&cardPassword=%s", bo.getRealName(), bo.getIdCardNumber(), bo.getMobile(), bo.getCardPass());
            log.info("请求信息:{}|{}", url, headers);
            ResponseEntity<JSONObject> resEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
            JSONObject body = resEntity.getBody();
            log.info("响应信息:{}", body);
            if (body.getInteger("statusCode") != 200){
                throw new BusinessException(body.getString("message"));
            }
        } catch (Exception e) {
            if (e instanceof BusinessException){
                throw e;
            }
            else {
                log.error("开卡接口异常:{}", bo, e);
            }
        }
    }

    private Map<String, String> headers(){
        long s = System.currentTimeMillis() / 1000L;
        String sign = DigestUtil.sha1Hex(healthCardMfProperties.getKey() + healthCardMfProperties.getSecret() + s);
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/x.dyhz-app.v1+json");
        headers.put("X-DYHZ-CLIENT", "DOCTOR");
        headers.put("X-API-Key", healthCardMfProperties.getKey());
        headers.put("X-Timestamp", "" + s);
        headers.put("X-Signature", sign);
        headers.put("X-PartnerId", "1");
        headers.put("X-UserId", "1");
        return headers;
    }

}




