package com.captain.land.interceptor;

import com.captain.land.error.LandErrorCode;
import com.captain.land.error.LandException;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * Controller 拦截器 做错误码
 * Created by wurenzhi
 */
@Component
@Slf4j
public class ControllerInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException:", e);
            String msg = e.getMessage();
            return LandErrorCode.PARAM_ERROR.newDataResult(msg);
        } catch (LandException e) {
            log.error("LandException:", e);
            String msg = e.getMessage();
            return e.getErrorCode().newDataResult(msg);
        } catch (Exception e) {
            log.error("未定义异常:", e);
            String msg = e.getMessage();
            return LandErrorCode.UNKNOW_ERROR.newDataResult(msg);
        }
    }
}
