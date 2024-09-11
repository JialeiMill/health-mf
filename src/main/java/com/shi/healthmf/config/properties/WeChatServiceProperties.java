package com.shi.healthmf.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wechat")
public class WeChatServiceProperties {

    private String appId;

    private String appSecret;

}
