package com.captain.land.common.result;

/**
 * Created by wurenzhi on 2016/12/26.
 */
public class Result<D> {

    private static final String SUCCESS_CODE = "00000000";
    private D data;
    private boolean success;
    private String code;
    private String message;

    public Result() {
    }

    public static <D> Result<D> wrapSuccessfulResult(D data) {
        Result result = new Result();
        result.data = data;
        result.success = true;
        result.code = "00000000";
        return result;
    }

    public static <T> Result<T> wrapSuccessfulResult(String message, T data) {
        Result result = new Result();
        result.data = data;
        result.success = true;
        result.code = "00000000";
        result.message = message;
        return result;
    }

    public static <D> Result<D> wrapErrorResult(String code, String message) {
        Result result = new Result();
        result.success = false;
        result.code = code;
        result.message = message;
        return result;
    }

    public D getData() {
        return this.data;
    }

    public Result<D> setData(D data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<D> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Result<D> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Result<D> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("success=");
        sb.append(this.success);
        sb.append(",");
        sb.append("code=");
        sb.append(this.code);
        sb.append(",");
        sb.append("message=");
        sb.append(this.message);
        sb.append(",");
        sb.append("data=");
        sb.append(this.data);
        sb.append("}");
        return sb.toString();
    }
}
