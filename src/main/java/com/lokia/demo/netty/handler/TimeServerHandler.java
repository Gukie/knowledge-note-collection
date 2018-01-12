package com.lokia.demo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gushu
 * @date 2018/01/12
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(TimeServerHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		
		ByteBuf newLine = ctx.alloc().buffer();
		newLine.writeBytes("\n".getBytes());
		ctx.writeAndFlush(newLine);
		ByteBuf time = ctx.alloc().buffer(4);
		time.writeInt((int) (System.currentTimeMillis()/1000+2));
		
		final ChannelFuture channelFuture = ctx.writeAndFlush(time);
		channelFuture.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(channelFuture != future){
					logger.error("not the same future");
					return;
				}
//				ctx.close();
				logger.error("echo succefully");
				System.err.println("echo succefully");
			}
		});
		ByteBuf hello = ctx.alloc().buffer();
		hello.writeBytes("hello".getBytes("utf-8"));
		ctx.writeAndFlush(hello);
	}
}
