package com.sq.common.util;

import com.sq.domain.Message;
import com.sq.domain.enums.*;

/**
 * Created by qishang on 2017/6/8.
 */
public class MessageUtil {
    public static Message success(String data) {
        return new Message(Result.SUCCESS, Code.SUCCESS.getValue(), data);
    }

    public static Message success() {
        return success("");
    }

    public static Message failed(int code) {
        return new Message(Result.FAILED, code);
    }

    public static Message failed(int code, String msg) {
        return new Message(Result.FAILED, Integer.valueOf(code), msg);
    }

    public static Message error() {
        return new Message(Result.FAILED, "");
    }

    public static Message error(int code) {
        return new Message(Result.FAILED, code);
    }

    public static boolean isSuccess(Message message) {
        return Result.SUCCESS.equals(message.getResult());
    }
}
