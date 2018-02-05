package com.lokia.demo.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * refer: http://jzhihui.iteye.com/blog/1271122
 * 
 * JVM中最多可以创建的线程数量的测试
 * @author gushu
 * @date 2018/02/05
 */
public class MaxThreadNumTest  {

	public static void main(String[] args) {
	
		AtomicInteger threadNum = new AtomicInteger();
		
		while(true){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					System.err.println(threadNum.incrementAndGet());
					while (true) {
						try {
							Thread.sleep(Integer.MAX_VALUE);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
					}
					
				}
			});

			thread.start();
		}
	
	}
	
	

}
