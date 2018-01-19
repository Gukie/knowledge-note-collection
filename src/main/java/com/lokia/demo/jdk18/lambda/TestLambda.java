package com.lokia.demo.jdk18.lambda;

/**
 * @author gushu
 * @date 2018/01/19
 */
public class TestLambda {

	interface IntegerMath{
		int operation(int a, int b);
	}
	
	public int doOperation(int a,int b,IntegerMath operation){
		return operation.operation(a, b);
	}
	
	public static void main(String[] args) {
		TestLambda tl = new TestLambda();
		
		IntegerMath addition = (a,b) -> a+b;
		IntegerMath multiple = (a,b) -> a*b;
		int multipleResult= tl.doOperation(3, 4, multiple);
		int additionResult= tl.doOperation(3, 4, addition);
		
		System.out.println("multiple:"+multipleResult);
		System.out.println("addition:"+additionResult);
	}
}
