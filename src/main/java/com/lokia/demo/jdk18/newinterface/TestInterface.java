package com.lokia.demo.jdk18.newinterface;

/**
 * @author gushu
 * @date 2018/01/22
 */
public interface TestInterface {

	default int getNumber(){
	
		return 100;
	}
	
	static int hello(){
		return 10000;
	}
	
	int setNumber();
}
