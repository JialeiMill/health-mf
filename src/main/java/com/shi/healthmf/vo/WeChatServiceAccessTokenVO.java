package com.shi.healthmf.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatServiceAccessTokenVO implements Serializable {

    private static final long serialVersionUID = -2466867710446610001L;

    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String is_snapshotuser;
    private String unionid;

}
