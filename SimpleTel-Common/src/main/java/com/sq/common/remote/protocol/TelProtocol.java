package com.sq.common.remote.protocol;

import com.sq.common.remote.message.HeartBreak;
import com.sq.common.remote.message.TelMessage;
import com.sq.common.util.HeaderUtil;
import com.sq.common.util.SerialUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by qishang on 2017/6/9.
 * 通讯协议
 */
public class TelProtocol {
    public static Object decode(ByteBuf byteBuf) {
        //总长度
        int length = byteBuf.readableBytes();
        //头长度
        int headerLength = byteBuf.readInt();
        //头部数组
        byte[] headerData = new byte[headerLength];
        byteBuf.readBytes(headerData);
        //消息体数组
        int bodyLength = length - 4 - headerLength;
        if (bodyLength > 0) {
            byte[] bodyData = new byte[bodyLength];
            byteBuf.readBytes(bodyData);
        }
        switch (HeaderUtil.decode(headerData)) {
            case Request:
                break;
            case Response:
                break;
            case HeartBreak:
                return new HeartBreak();
            default:
                return null;
        }
        return null;
    }

    public static ByteBuf encode(TelMessage msg) throws IOException {
        byte[] header;
        switch (HeaderUtil.encode(msg)) {
            case HeartBreak:
                header = new byte[]{HeaderUtil.TypeEnum.HeartBreak.getValue()};
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(4 + header.length);
                buf.writeInt(header.length);
                buf.writeBytes(header);
                return buf;
            case Response:
                header = new byte[]{HeaderUtil.TypeEnum.Response.getValue()};
                break;
            case Request:
                header = new byte[]{HeaderUtil.TypeEnum.Request.getValue()};
                break;
            default:
                return null;
        }
        byte[] body = SerialUtil.Serial(msg);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(4 + header.length+body.length);
        buf.writeInt(header.length);
        buf.writeBytes(header);
        buf.writeBytes(body);
        return buf;
    }
}
