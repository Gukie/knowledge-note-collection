package com.lokia.demo.jdk18.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * refer: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
 * 
 * @author gushu
 * @date 2018/01/17
 */
public class TestStream {

	public static void main(String[] args) {

		// int sum = widgets.stream()
		// .filter(w -> w.getColor() == RED)
		// .mapToInt(w -> w.getWeight())
		// .sum();

		// for(int i = 0;i<5;i++){
		// System.err.println("==============================");
		// IntStream stream = IntStream.range(0,5).parallel().map(x -> x*2);
		// stream.forEach(System.err::println);
		// }

		

		 List<Integer> teStrings =
		 Arrays.asList(46,45,1,23,6,20,1,3,5,66,18,0,2,89);
		// Stream<Integer> tStream = teStrings.stream();
		 printStream(teStrings.parallelStream(),true);
		 printStream(teStrings.stream(),true);
//		 
		 testSideEffects(true);
		 testUnordered(false);
	}

	private static void testUnordered(boolean isSkipped) {
		if(isSkipped){
			return;
		}
		List<Integer> originalData = new ArrayList<>();
		IntStream.range(1, 10).forEach(e -> originalData.add(e));
		originalData.forEach(System.out::println);
		System.out.println("************************ un-ordered ************************");
		for(int i = 0;i<5;i++){
			Stream<Integer> pStream = originalData.parallelStream();
			pStream.unordered().forEach(System.out::println);
//			pStream.forEach(System.out::println);
			System.out.println();
		}
	}

	// 
	private static void testSideEffects(boolean isSkipped) {
		if(isSkipped){
			return;
		}
		List<String> originalData = new ArrayList<>();
		IntStream.range(1, 10).forEach(e -> originalData.add("one"+String.valueOf(e)));
		
		// the result element order will be the same for every run 
//		for (int i = 0; i < 5; i++) {
//			System.err.println("==============================");
//			Stream<String> streamStr = originalData.parallelStream();
//			List<String> result = streamStr.filter(e -> e.contains("one")).collect(Collectors.toList());
//			result.stream().forEach(System.err::println);
//		}

		// the result element order will be different for every run. Since forEach has sideEffect  
		for (int i = 0; i < 5; i++) {
			System.err.println("==============================");
			List<String> result = new ArrayList<>();
			Stream<String> streamStr = originalData.parallelStream();
			streamStr.filter(e -> e.contains("one")).forEach(e -> result.add(e));
			result.stream().forEach(System.err::println);
		}
		
	}

	private static void printStream(Stream<Integer> tStream,boolean isSkipped) {
		if(isSkipped){
			return;
		}
		System.err.println("****************************isParallel:" + tStream.isParallel());
		Stream<Integer> sortedStream = tStream.filter(item -> item > 10).sorted();
		sortedStream.forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
				System.err.println(t);
			}
		});
	}

}
