package com.lokia.demo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardServerHandler extends ChannelHandlerAdapter {
	
	Logger logger = LoggerFactory.getLogger(DiscardServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		super.channelRead(ctx, msg);
		System.out.println("received msg:"+msg);
		logger.debug("received msg:{}",msg);
		if(msg instanceof ByteBuf){
			String msgStr =getMsgStr((ByteBuf)msg);
			System.out.println("received msg:"+msgStr);
			logger.debug("received msg:{}",msgStr);
		}
	}

	private String getMsgStr(ByteBuf buf) {
		int size = buf.readableBytes();
		byte[] bytes = new byte[size];
		int index = 0;
		while(buf.readableBytes()>0){
			byte bt = buf.readByte();
			bytes[index] = bt;
			index++;
		}
		return new String(bytes);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		System.err.println("exception caught:"+cause);
		logger.error("exception caught:",cause);
	}

}
