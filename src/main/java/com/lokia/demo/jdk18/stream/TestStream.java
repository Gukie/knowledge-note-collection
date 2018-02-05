package com.lokia.demo.jdk18.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * refer:
 * https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
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

		List<Integer> teStrings = Arrays.asList(46, 45, 1, 23, 6, 20, 1, 3, 5, 66, 18, 0, 2, 89);
		// Stream<Integer> tStream = teStrings.stream();
		printStream(teStrings.parallelStream(), true);
		printStream(teStrings.stream(), true);
		//
		testSideEffects(true);
		testUnordered(true);
		testStatelessBehaviorWithMultipleThread(false);
		testStatelessBehaviorWithoutMultipleThread(true);
	}

	private static void testUnordered(boolean isSkipped) {
		if (isSkipped) {
			return;
		}
		List<Integer> originalData = new ArrayList<>();
		IntStream.range(1, 10).forEach(e -> originalData.add(e));
		originalData.forEach(System.out::println);
		System.out.println("************************ un-ordered ************************");
		for (int i = 0; i < 5; i++) {
			Stream<Integer> pStream = originalData.parallelStream();
			pStream.unordered().forEach(System.out::println);
			// pStream.forEach(System.out::println);
			System.out.println();
		}
	}

	//
	private static void testSideEffects(boolean isSkipped) {
		if (isSkipped) {
			return;
		}
		List<String> originalData = new ArrayList<>();
		IntStream.range(1, 10).forEach(e -> originalData.add("one" + String.valueOf(e)));

		// the result element order will be the same for every run
		// for (int i = 0; i < 5; i++) {
		// System.err.println("==============================");
		// Stream<String> streamStr = originalData.parallelStream();
		// List<String> result = streamStr.filter(e ->
		// e.contains("one")).collect(Collectors.toList());
		// result.stream().forEach(System.err::println);
		// }

		// the result element order will be different for every run. Since
		// forEach has sideEffect
		for (int i = 0; i < 5; i++) {
			System.err.println("==============================");
			List<String> result = new ArrayList<>();
			Stream<String> streamStr = originalData.parallelStream();
			streamStr.filter(e -> e.contains("one")).forEach(e -> result.add(e));
			result.stream().forEach(System.err::println);
		}

	}

	private static void printStream(Stream<Integer> tStream, boolean isSkipped) {
		if (isSkipped) {
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

	/**
	 * 
	 *
	 * link: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
	 * <p>
	 * 如果以下code在多线程环境下,每次运行的结果，会是不一样的. 因为 map里面的 behavioral parameter是同步操作
	 * <blockquote><pre>
	 * Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
     * stream.parallel().map(e -> { if (seen.add(e)) return 0; else return e; })
     * </pre></blockquote>
	 * </p>
	 * @param isSkipped
	 */
	private static void testStatelessBehaviorWithMultipleThread(boolean isSkipped) {

		if(isSkipped){
			return;
		}
		// Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
		// stream.parallel().map(e -> { if (seen.add(e)) return 0; else return
		// e; }).
		
		
		int collectionNum = 8544;
		Random random = new Random();
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 0;i<collectionNum;i++){
			list.add(random.nextInt(492099));
		}
		
//		long count = Arrays.asList(1,2,3).stream().filter(e->e!=3).count();
//		System.err.println(count);
		

		int round = 5;
//		Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
		for (int i = 0; i < round; i++) {
			System.err.println("**************** round-"+i+" **************** ");
			System.err.println();
			int threadNum = Runtime.getRuntime().availableProcessors()+1;
			Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
			AtomicLong filteredCount = new AtomicLong();
			
			CyclicBarrier startBarrier = new CyclicBarrier(threadNum);
			CyclicBarrier endBarrier = new CyclicBarrier(threadNum+1);
			for(int threadIndex = 0; threadIndex < threadNum;threadIndex++){
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						System.err.println(Thread.currentThread().getName()+" start, count:"+seen.size());
						try {
							startBarrier.await();
						} catch (InterruptedException | BrokenBarrierException e2) {
							e2.printStackTrace();
						}
						
						long count = list.stream().parallel().filter(e -> e >20).map(e -> {
							if (seen.add(e)) {
								return 0;
							}
							return e;
						}).filter(e -> e >0).count();
						
						System.err.println(Thread.currentThread().getName()+"-filtered-number:" + count);
						System.err.println(Thread.currentThread().getName()+" end, size:" + seen.size());
						filteredCount.addAndGet(count);
						
						try {
							endBarrier.await();
						} catch (InterruptedException | BrokenBarrierException e2) {
							e2.printStackTrace();
						}
					}
				});
				thread.setName("round-" + i + "-thread-" + threadIndex);
				thread.start();
			}
			
			try {
				endBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.err.println("**************** round-"+i+", filtered count:"+filteredCount.get());
		}
	}
	private static void testStatelessBehaviorWithoutMultipleThread(boolean isSkipped) {

		if(isSkipped){
			return;
		}
		
		int collectionNum = 8544;
		Random random = new Random();
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 0;i<collectionNum;i++){
			list.add(random.nextInt(492099));
		}
		
//		long count = Arrays.asList(1,2,3).stream().filter(e->e!=3).count();
//		System.err.println(count);
		

		int round = 235;
//		Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
		for (int i = 0; i < round; i++) {
			Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());
			long count = list.stream().parallel().filter(e -> e >20).map(e -> {
				if (seen.add(e)) {
					return 0;
				}
				return e;
			}).filter(e -> e >0).count();
			
			System.err.println("round-"+i+"-filtered-number:" + count);
		}
	}

}
