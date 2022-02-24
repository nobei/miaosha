package com.jwh.miaosha.Expection;

import lombok.Data;

/**
 * @author ：jwh
 * @description：TODO
 * @date ：2022/2/23 19:58
 */
public enum ExceptionErrorCode {
    GoodNotFond("GoodNotFound",10001),
    GoodSellOut("GoodSelleOut",10002),
    GoodIsNone("GoodIsNode",10000);

    private String errorMessage;
    private int errorCode;
    ExceptionErrorCode(String errorMessage, int errorCode) {
        this.errorMessage= errorMessage;
        this.errorCode = errorCode;

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
