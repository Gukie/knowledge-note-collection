package com.lokia.demo.netty.server;

import com.lokia.demo.netty.handler.DiscardServerHandler;
import com.lokia.demo.netty.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gushu
 * @date 2018/01/12
 */
public class DiscardServer {

	private Logger logger = LoggerFactory.getLogger(DiscardServer.class);
	public static final int SERVER_PORT = 9000;
	
	public static void main(String[] args) {
		
		DiscardServer discardServer = new DiscardServer();
		discardServer.start();
	}
	
	public void start() {
		logger.debug("begin to start");
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new DiscardServerHandler(),new TimeServerHandler());
			}
		});
		serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
		serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			ChannelFuture channelFuture = serverBootstrap.bind(SERVER_PORT).sync();
			System.out.println("starting succesfully");
			logger.debug("starting succesfully");
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("fail to start:",e);
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
