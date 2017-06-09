package com.sq.common.handler;

import com.sq.common.remote.protocol.TelProtocol;
import com.sq.common.util.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;

/**
 * Created by qishang on 2017/6/8.
 */
public class TelDecoder extends LengthFieldBasedFrameDecoder {
    public TelDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = null;
        try {
            byteBuf = (ByteBuf) super.decode(ctx, in);
            if (null == byteBuf) {
                return null;
            }
            return TelProtocol.decode(byteBuf);
        } catch (Exception e) {
            // 这里关闭后， 会在pipeline中产生事件，通过具体的close事件来清理数据结构
            ChannelUtil.close(ctx.channel());
        } finally {
            if (null != byteBuf) {
                byteBuf.release();
            }
        }
        return null;
    }
}

