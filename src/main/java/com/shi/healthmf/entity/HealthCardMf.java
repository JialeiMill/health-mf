package com.shi.healthmf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 健康卡-觅方
 * @TableName health_card_mf
 */
@TableName(value ="health_card_mf")
@Data
public class HealthCardMf implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 卡密
     */
    @TableField(value = "card_pass")
    private String cardPass;

    /**
     * 状态 0 正常 1 使用 2 绑定
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 绑定人用户ID
     */
    @TableField(value = "bind_user_id")
    private Long bindUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 绑定时间
     */
    @TableField(value = "bind_time")
    private LocalDateTime bindTime;

    /**
     * 绑定手机号
     */
    @TableField(value = "bind_phone")
    private String bindPhone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}