package com.pinyougou.vo;

import java.io.Serializable;

public class Result implements Serializable {
    private boolean success;
    private String message;

    /**
     * 如果操作成功可以使用该方法
     * @param message 信息
     * @return 操作结果对象
     */
    public static Result ok(String message) {
        return new Result(true,message);
    }

    /**
     * 如果操作失败可以使用该方法
     * @param message 信息
     * @return 操作结果对象
     */
    public static Result fail(String message) {
        return new Result(false,message);
    }

    public Result() {
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
