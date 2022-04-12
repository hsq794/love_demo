package com.hy.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Result {


    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result("200", "操作成功!", null);
    }

    public static Result success(Object data) {
        return new Result("200", "操作成功!", data);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result("500", "系统错误", null);
    }


}
