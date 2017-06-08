package com.sq.domain;

import com.sq.domain.enums.Code;
import com.sq.domain.enums.Result;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * @see com.sq.domain.enums.Result
     */
    private int result;
    /**
     * @see com.sq.domain.enums.Code
     */
    private int code;
    private String data;


    public Message() {
    }

    public Message(int result, int code) {
        this.result = result;
        this.code = code;
    }

    public Message(int result, int code, String data) {
        this.result = result;
        this.code = code;
        this.data = data;
    }

    public Message(int result, String data) {
        this.result = result;
        this.data = data;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isHeartBreak() {
        return code == Code.HeartBeat.getValue();
    }

    public boolean isSuccess() {
        return result == Result.SUCCESS.getValue();
    }
}
