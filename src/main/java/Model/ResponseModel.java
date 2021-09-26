package Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseModel<T> implements Serializable {
    ResponseStatus statusCode;
    T data;
    String message;

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
