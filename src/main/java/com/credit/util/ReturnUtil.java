package com.credit.util;


public class ReturnUtil<T> {
    private static final int CODE_SUCCESS = 1;

    private static final int CODE_FAIL = 500;

    private static final int CODE_ERROR = 0;

    private static final int CODE_NO_LOGIN = 300;

    private int code;

    private String msg;

    private T data;

    public ReturnUtil(int code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public static <T> com.credit.util.ReturnUtil<T> success() {
        return new com.credit.util.ReturnUtil<T>(CODE_SUCCESS, "success", null);
    }
    public static <T> com.credit.util.ReturnUtil<T> success(String message) {
        return new com.credit.util.ReturnUtil<T>(CODE_SUCCESS, message, null);
    }
    public static <T> com.credit.util.ReturnUtil<T> success(T data) {
        return new com.credit.util.ReturnUtil<T>(CODE_SUCCESS, "success", data);
    }
    public static <T> com.credit.util.ReturnUtil<T> success(String message, T data) {
        return new com.credit.util.ReturnUtil<T>(CODE_SUCCESS, message, data);
    }

    public static <T> com.credit.util.ReturnUtil<T> error() {
        return new com.credit.util.ReturnUtil<T>(CODE_ERROR, "fail", null);
    }
    public static <T> com.credit.util.ReturnUtil<T> error(String message) {
        return new com.credit.util.ReturnUtil<T>(CODE_ERROR, message, null);
    }
    public static <T> com.credit.util.ReturnUtil<T> error(T data) {
        return new com.credit.util.ReturnUtil<T>(CODE_ERROR, "fail", data);
    }
    public static <T> com.credit.util.ReturnUtil<T> error(String message, T data) {
        return new com.credit.util.ReturnUtil<T>(CODE_ERROR, message, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
