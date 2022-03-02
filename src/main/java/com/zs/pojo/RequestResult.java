package com.zs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 请求结果类
 * @Created by zs on 2022/2/23.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestResult {
    private int code;
    private String message;
    private Object data;
}
