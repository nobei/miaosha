package com.jwh.miaosha.Expection;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/2/23 20:08
 */
public class ExceptionLogical extends RuntimeException {
    ExceptionErrorCode exceptionErrorCode;

    public ExceptionLogical(){
        super();
    }

    public ExceptionLogical(ExceptionErrorCode exceptionErrorCode){
        super(exceptionErrorCode.getErrorMessage());
        this.exceptionErrorCode = exceptionErrorCode;
    }

    @Override
    public String getMessage() {
        return exceptionErrorCode.getErrorMessage();
    }
}
