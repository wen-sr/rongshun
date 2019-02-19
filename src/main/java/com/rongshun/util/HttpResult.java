package com.rongshun.util;

/**
 * 映射http 请求结果
 * @author kun.zhang@downjoy.com
 *
 *
 */
public class HttpResult {
    private Integer statusCode;//返回的状态码
    private String data;//返回的数据
    /**
     * @return the statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    public HttpResult(Integer statusCode, String data) {
        super();
        this.statusCode = statusCode;
        this.data = data;
    }

    public HttpResult() {
        super();
    }
}