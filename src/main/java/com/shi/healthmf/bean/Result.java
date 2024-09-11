package com.shi.healthmf.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public Result<T> success() {
        return this.success("success");
    }

    public Result<T> success(String msg) {
        this.code = 200;
        this.msg = msg;
        return this;
    }

    public Result<T> fail() {
        return this.fail("fail");
    }

    public Result<T> fail(String msg) {
        this.code = 500;
        this.msg = msg;
        return this;
    }

    public Result<T> notLogin() {
        return this.notLogin("未登录");
    }

    public Result<T> notLogin(String msg) {
        this.code = 401;
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

}
