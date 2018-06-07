package com.captain.land.error;


import com.captain.land.common.result.Result;
import lombok.Data;

/**
 * Created by wurenzhi on 2016/12/27.
 */
@Data
public class ErrorCode {

    //错误编码
    private String code;

    //错误信息
    private String message;

    public ErrorCode() {
    }

    public ErrorCode(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public <T> Result newDataResult(T data){
        Result result = new Result();
        result.setSuccess(false);
        result.setData(data);
        result.setData(this.code);
        result.setMessage(this.message);
        return result;
    }
}
