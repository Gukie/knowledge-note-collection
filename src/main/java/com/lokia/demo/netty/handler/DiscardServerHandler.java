package com.lokia.demo.netty.handler;

import java.io.UnsupportedEncodingException;

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
		readMsg(msg);
		echoMsg(ctx,msg);
	}

	private void echoMsg(ChannelHandlerContext ctx,Object receivedMsg) {
		String echoMsgPrefix = "I have received ";
		String receivedMsgStr = buildMsgStr((ByteBuf)receivedMsg);
		System.out.println("echo to server:"+(echoMsgPrefix+receivedMsgStr));
		logger.debug("echo to server:{}",(echoMsgPrefix+receivedMsgStr));
		ctx.writeAndFlush(echoMsgPrefix+receivedMsgStr);
		
	}

	private void readMsg(Object msg) {
		System.out.println("received msg:"+msg);
		logger.debug("received msg:{}",msg);
		if(msg instanceof ByteBuf){
			String msgStr =buildMsgStr((ByteBuf)msg);
			System.out.println("received msg:"+msgStr);
			logger.debug("received msg:{}",msgStr);
		}
	}

	private String buildMsgStr(ByteBuf original) {
		ByteBuf currentData = original.copy(); // copy, in order to reuse.
		int size = currentData.readableBytes();
		byte[] bytes = new byte[size];
		int index = 0;
		while(currentData.readableBytes()>0){
			byte bt = currentData.readByte();
			bytes[index] = bt;
			index++;
		}
		try {
			return new String(bytes,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("Exception happens when building", e);
		}
		return "";
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		System.err.println("exception caught:"+cause);
		logger.error("exception caught:",cause);
	}

}
