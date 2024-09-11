package com.shi.healthmf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeChatServiceUserInfoVO implements Serializable {

    private static final long serialVersionUID = 8867172832594494304L;

    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
    private String unionid;

}
