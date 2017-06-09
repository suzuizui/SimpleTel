package com.sq.common.util;

import com.sq.common.remote.message.HeartBreak;
import com.sq.common.remote.message.TelMessage;
import com.sq.common.remote.message.TelRequest;
import com.sq.common.remote.message.TelResponse;

import java.nio.ByteBuffer;

/**
 * Created by qishang on 2017/6/9.
 */
public class HeaderUtil {
    public static TypeEnum decode(byte[] header) {
        byte version = header[0];
        return TypeEnum.getByValue(version);
    }

    public static TypeEnum encode(TelMessage msg) {
        if (msg instanceof HeartBreak) {
            return TypeEnum.HeartBreak;
        } else if (msg instanceof TelRequest) {
            return TypeEnum.Request;
        } else if (msg instanceof TelResponse) {
            return TypeEnum.Response;
        }
        return null;
    }

    /**
     * 消息类型枚举
     */
    public enum TypeEnum {
        //心跳
        HeartBreak((byte) 1),
        //请求
        Request((byte) 2),
        //回应
        Response((byte) 3);
        private byte value;

        TypeEnum(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }

        public static TypeEnum getByValue(byte value) {
            for (TypeEnum rle : TypeEnum.values()) {
                if (rle.value == value) {
                    return rle;
                }
            }
            return null;
        }
    }
}
