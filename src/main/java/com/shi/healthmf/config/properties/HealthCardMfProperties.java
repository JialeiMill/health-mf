package com.shi.healthmf.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "health-card")
public class HealthCardMfProperties {

    private String key;

    private String secret;

    private String bakFwUrl;

}
