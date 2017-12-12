package com.adbaitai.land.error;


import com.adbaitai.land.common.result.Result;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2016/12/27.
 */
@Getter
@Setter
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
        return (new Result()).setSuccess(false).setData(data).setCode(this.getCode()).setMessage(this.getMessage());
    }
}
