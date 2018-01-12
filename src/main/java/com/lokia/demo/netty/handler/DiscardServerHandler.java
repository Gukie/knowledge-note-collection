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
//			PooledByteBuf bytess = msg;
//			String msgStr = new String(bytes);
//			System.out.println("received msg:"+msgStr);
//			logger.debug("received msg:{}",msgStr);
//			ByteBuf buf = (ByteBuf) msg;
//			byte[] bytes = new byte[buf.capacity()];
//			buf.readBytes(bytes);
//			String msgStr = new String(bytes);
			
			ByteBuf buf = (ByteBuf) msg;
			String msgStr = buf.toString();
			System.out.println("received msg:"+msgStr);
			logger.debug("received msg:{}",msgStr);
		}
		
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		System.err.println("exception caught:"+cause);
		logger.error("exception caught:",cause);
	}

}
