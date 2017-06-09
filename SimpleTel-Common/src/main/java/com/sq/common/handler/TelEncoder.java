package com.sq.common.handler;

import com.sq.common.remote.message.TelMessage;
import com.sq.common.remote.protocol.TelProtocol;
import com.sq.common.util.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by qishang on 2017/6/8.
 */
public class TelEncoder extends MessageToByteEncoder<TelMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TelMessage msg, ByteBuf out) throws Exception {
        try {
            ByteBuf result = TelProtocol.encode(msg);
            if (result != null) {
                out.writeBytes(result);
            }
        } catch (Exception e) {
            // 这里关闭后， 会在pipeline中产生事件，通过具体的close事件来清理数据结构
            ChannelUtil.close(ctx.channel());
        }
    }
}
