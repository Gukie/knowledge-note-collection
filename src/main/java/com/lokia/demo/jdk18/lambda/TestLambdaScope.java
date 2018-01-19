package com.lokia.demo.jdk18.lambda;

import java.util.function.Consumer;

/**
 * @author gushu
 * @date 2018/01/19
 */
public class TestLambdaScope {
	public int x = 0;

    class FirstLevel {

        public int x = 1;

        void methodInFirstLevel(String x) {
            
            // The following statement causes the compiler to generate
            // the error "local variables referenced from a lambda expression
            // must be final or effectively final" in statement A:
            //
//             x = 99;
            
            Consumer<Integer> myConsumer = (y) ->  // y is the value passed from myConsumer.accept()
            {
                System.out.println("x = " + x); // Statement A
                System.out.println("y = " + y);
                System.out.println("this.x = " + this.x);
                System.out.println("LambdaScopeTest.this.x = " +
                		TestLambdaScope.this.x);
            };

            myConsumer.accept(22);

        }
    }

    public static void main(String... args) {
    	TestLambdaScope st = new TestLambdaScope();
    	TestLambdaScope.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel("hello");
    }
}
