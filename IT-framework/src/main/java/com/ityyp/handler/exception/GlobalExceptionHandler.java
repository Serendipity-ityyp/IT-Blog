package com.ityyp.handler.exception;

import com.ityyp.domain.ResponseResult;
import com.ityyp.enums.AppHttpCodeEnum;
import com.ityyp.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常!{}",e);
        //从异常对象中获取提示信息并封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        //打印异常信息
        log.error("出现了异常!{}", e);
        //从异常对象中获取提示信息并封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),AppHttpCodeEnum.SYSTEM_ERROR.getMsg());
    }
}
