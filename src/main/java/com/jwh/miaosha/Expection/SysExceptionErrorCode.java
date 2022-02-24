package com.jwh.miaosha.Expection;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/3/5 18:16
 */
public enum SysExceptionErrorCode {
    JSONParserException("JsonException", -10000);

    private String errorMessage;
    private int errorCode;


    SysExceptionErrorCode(String errorMessage, int errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }}
