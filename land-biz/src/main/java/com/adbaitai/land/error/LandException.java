package com.adbaitai.land.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

/**
 * Created by wurenzhi on 2016/12/27.
 */
@Setter
@Getter
public class LandException extends RuntimeException{

    //错误编码
    private String code;

    //错误信息
    private String message;

    //错误信息
    private ErrorCode errorCode;

    public LandException() {
    }

    public LandException(ErrorCode errorCode) {
        Assert.notNull(errorCode,"errorCode不能为空");
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String toString() {
        return "LandException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
