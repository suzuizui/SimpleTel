//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sq.domain;

import com.sq.domain.enums.Result;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private Result result;
    private int code;
    private String message;
    private String data;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message() {
    }

    public Message(Result result, int code) {
        this.result = result;
        this.code = code;
    }

    public Message(Result result, Integer code, String message) {
        this.result = result;
        this.code = code.intValue();
        this.message = message;
    }

    public Message(Result result, int code, String message, String data) {
        this.result = result;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Message(Result result, String data) {
        this.result = result;
        this.data = data;
    }

    public Message(Result result, int code, String data) {
        this.result = result;
        this.code = code;
        this.data = data;
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
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
}
