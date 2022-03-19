package com.jwh.miaosha.Expection;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/3/5 18:28
 */
public class SystemException extends RuntimeException{
    SysExceptionErrorCode systemException;
    String message;
    public SystemException(SysExceptionErrorCode jsonParserException, String msg) {
        super(msg);
        this.systemException = jsonParserException;
        message = msg;
    }

    @Override
    public String getMessage() {
        return systemException.getErrorMessage()+message;
    }
}
