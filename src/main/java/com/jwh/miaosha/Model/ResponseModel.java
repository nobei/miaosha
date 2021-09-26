package com.jwh.miaosha.Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseModel<T> implements Serializable {
    ResponseStatus statusCode;
    T data;
    String message;

    public ResponseModel(){

    }

    public ResponseModel(ResponseBuild responseBuild){
        this.statusCode = responseBuild.statusCode;
        this.data = (T) responseBuild.data;
        this.message = responseBuild.message;
    }

    public static class ResponseBuild<T>{
        private ResponseStatus statusCode;
        private T data;
        private String message;

        public ResponseStatus getStatusCode() {
            return statusCode;
        }

        public ResponseBuild setStatusCode(ResponseStatus statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public T getData() {
            return data;
        }

        public ResponseBuild setData(T data) {
            this.data = data;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public ResponseBuild setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseModel Build(){
            return new ResponseModel(this);
        }
    }

    public ResponseModel(T data, ResponseStatus responseStatus){

        this.statusCode = responseStatus;
        this.data = data;
    }

    public ResponseModel(T data,ResponseStatus responseStatus,String message){
        this.statusCode = responseStatus;
        this.data = data;
        this.message = message;
    }

}
