package com.lokia.demo.netty.server;

import io.netty.buffer.ChannelBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void inboundBufferUpdated(ChannelHandlerContext channelHandlerContext) throws Exception {


    }

    @Override
    public ChannelBuf newInboundBuffer(ChannelHandlerContext channelHandlerContext) throws Exception {
        return null;
    }

    @Override
    public void freeInboundBuffer(ChannelHandlerContext channelHandlerContext, ChannelBuf channelBuf) throws Exception {

    }
}
