package cn.zenghr.travel.websiteapi.advice;

import cn.zenghr.travel.tripcore.common.R;
import cn.zenghr.travel.tripcore.common.UserInfoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R handleBindException(BindException ex) {
        return R.error(400, formatErrorMessage(ex.getBindingResult()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R handleConstraintViolationException(ConstraintViolationException ex) {
        return R.error(400, ex.getMessage());
    }

    /**
     * 捕获 自定义异常
     * @param ex UserInfoException
     * @return 统一响应
     */
    @ExceptionHandler({UserInfoException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R handleUserInfoException(UserInfoException ex) {
        return R.error(ex.getMessage());
    }


    /**
     * 捕获系统异常
     * @param ex RuntimeException
     * @return 统一响应
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace();
        return R.error("系统异常，请联系管理员");
    }

    /**
     * 格式化 异常信息
     * @param bindingResult 异常数据
     * @return 格式化信息字符串
     */
    private String formatErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError fieldError : bindingResult.getAllErrors()) {
            sb.append(fieldError.getDefaultMessage());
        }
        return sb.toString();
    }

}
