package com.lokia.demo.jdk18.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author gushu
 * @date 2018/01/17
 */
public class TestStream {

	public static void main(String[] args) {
		
//		int sum = widgets.stream()
//                .filter(w -> w.getColor() == RED)
//                .mapToInt(w -> w.getWeight())
//                .sum();

		
		List<Integer> teStrings = Arrays.asList(46,45,1,23,6,20,1,3,5,66,18,0,2,89);
//		Stream<Integer> tStream = teStrings.stream();
		printStream(teStrings.parallelStream());
		printStream(teStrings.stream());
		

	}

	private static void printStream(Stream<Integer> tStream) {
		System.err.println("****************************isParallel:"+tStream.isParallel());
		Stream<Integer> sortedStream = tStream.filter(item ->item>10).sorted();
		sortedStream.forEach(new Consumer<Integer>(){
			@Override
			public void accept(Integer t) {
				System.err.println(t);
			}
		});
	}

}
