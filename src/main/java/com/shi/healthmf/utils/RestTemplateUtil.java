package com.shi.healthmf.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/*
 * RestTemplate工具类
 * @author: Shijialei
 * @date: 2023/8/15
 */
public class RestTemplateUtil {

    public static <T> HttpEntity<T> generateEntity(T data, Map<String, String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null){
            for (String key : headers.keySet()) {
                httpHeaders.set(key, headers.get(key));
            }
        }
        return new HttpEntity<>(data, httpHeaders);
    }

}
