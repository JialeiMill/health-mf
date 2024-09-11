package com.shi.healthmf.excs;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
