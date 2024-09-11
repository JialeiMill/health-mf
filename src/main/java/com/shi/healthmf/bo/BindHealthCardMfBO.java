package com.shi.healthmf.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("绑定觅方健康卡参数")
public class BindHealthCardMfBO implements Serializable {

    private static final long serialVersionUID = -4097215922311525102L;
    @ApiModelProperty("卡密")
    private String cardPass;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("身份证号码")
    private String idCardNumber;
    @ApiModelProperty("手机号")
    private String mobile;

}


