package com.sq.common.util;

import com.sq.domain.Message;
import com.sq.domain.enums.Code;
import com.sq.domain.enums.Result;

/**
 * Created by qishang on 2017/6/8.
 */
public class MessageUtil {
    public static Message HeartBeat(boolean flag) {
        return get(Code.HeartBeat, flag, "");
    }

    public static Message success(String data) {
        return get(Code.COOMME, true, data);
    }

    public static Message fail(String data) {
        return get(Code.COOMME, false, data);
    }

    private static Message get(Code code, boolean flag, String data) {
        if (flag) {
            return new Message(Result.SUCCESS.getValue(), code.getValue(), data);
        } else {
            return new Message(Result.FAILED.getValue(), code.getValue(), data);
        }
    }
}
