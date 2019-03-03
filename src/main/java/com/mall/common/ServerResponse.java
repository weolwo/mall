package com.mall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 封装返回前台的数据
 */
//保证序列化json的时候,如果是null的对象,key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private Integer staus;
    private String message;
    private T data;

    private ServerResponse(Integer staus, String message, T data) {
        this.staus = staus;
        this.message = message;
        this.data = data;
    }

    private ServerResponse(Integer staus) {
        this.staus = staus;
    }

    private ServerResponse(Integer staus, String message) {
        this.staus = staus;
        this.message = message;
    }

    private ServerResponse(String message) {
        this.message = message;
    }

    private ServerResponse(T data) {
        this.data = data;
    }

    private ServerResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    private ServerResponse(Integer staus, T data) {
        this.staus = staus;
        this.data = data;
    }

    public Integer getStaus() {
        return staus;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    @JsonIgnore//使之不再序列化的结果之中
    public  boolean isSuccess(){
        return this.staus==ResponseCode.SUCCESS.getCode();
    }
    public static <T>  ServerResponse<T> createBySuccess(){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }
    public static <T>  ServerResponse<T> createBySuccessMessage(String message){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),message);
    }
    public static <T>  ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T>  ServerResponse<T> createBySuccess(String message,T data){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),message,data);
    }
    public static <T>  ServerResponse<T> createByError(){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMessage());
    }
    public static <T>  ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),errorMessage);
    }
    public static <T>  ServerResponse<T> createByError(int errorCode,String errorMessage){
        return new ServerResponse<>(errorCode,errorMessage);
    }
}
