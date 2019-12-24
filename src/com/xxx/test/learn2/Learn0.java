package com.xxx.test.learn2;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

public class Learn0 {

    //@Test
    public void test0() {
        long count0 = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .count();
        long count1 = IntStream.range(1, 100)
                .filter(n -> n % 2 == 0)
                .count();
        int sum = IntStream.range(1, 100)
                .filter(n -> n % 2 == 0)
                .sum();
        OptionalInt opt = IntStream.range(1, 100)
                .filter(n -> n % 2 == 0)
                .max();
        IntStream intStream = IntStream.range(1, 100)
                .filter(n -> n % 2 == 0);
        System.out.println(intStream.average());

        System.out.println(count0);
        System.out.println(count1);
        System.out.println(sum);
        opt.ifPresent(System.out::println);
    }

    //@Test
    public void test1() {
        Stream<int[]> stream0 = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                ).limit(4);
        stream0.forEach(arrays -> System.out.println(arrays[0]+","+arrays[1]+","+arrays[2]));
        //上下两者区别在于流的种类不同，上边的用boxed()改变为普通的流了，下边的还是特殊的int流，只能操控int数据，不能像上边一样去操控类和数组
        IntStream stream1 = IntStream.rangeClosed(1, 100)
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .map(b -> b+100)
                );

        Stream<double[]> stream2 = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(doubles -> doubles[2] % 1 == 0)
                ).limit(4);

        stream2.forEach(doubles -> System.out.println(doubles[0]+","+doubles[1]+","+doubles[2]));
    }

    //根据静态方法Stream.of()得到一个流。
    //@Test
    public void test2() {
        Stream<String> stream = Stream.of("Java8", "Lambda", "In", "Action");
        /*Optional<String> any = stream.filter(str -> str.length() == 2)
                .findAny();
        any.ifPresent(System.out::println);*/

        stream.map(String ::toUpperCase)
                .forEach(System.out::println);

        //得到一个空的流
        Stream<Object> emptyStream = Stream.empty();
        Optional<Object> opt = emptyStream.filter(a ->true)
                .findAny();
        Object obj = opt.orElse("没有值");
        System.out.println(obj);

    }

    //根据数组创建一个流
    //@Test
    public void test3() {
        int[] arrays = {1,3,5};
        int sum = Arrays.stream(arrays).sum();
        System.out.println(sum);
    }

    //file流
    public void fileTest() {
        long uniqueWords = 0;
        try(Stream<String> lines =
                    Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        }
        catch(IOException e){

        }
    }

    //iterate无限流
    //@Test
    public void test4() {
        Stream.iterate(0,n -> n+2)
                .limit(10)
                .forEach(System.out::println);
    }

    //斐波纳契元组数列
    //@Test
    public void test5() {
        Stream.iterate(new int[]{0, 1}, array ->{ int t =array[1]; array[1] = array[0] + array[1]; array[0] = t; return array;})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));

        Stream.iterate(new int[]{0, 1}, array ->new int[]{array[1],array[0] + array[1]})
                .limit(20)
                .forEach(t -> System.out.println("*(" + t[0] + "," + t[1] +")"));
        Stream.iterate(new int[]{0, 1}, array ->new int[]{array[1],array[0] + array[1]})
                .limit(20)
                .map(array -> array[0])
                .forEach(n -> System.out.println("斐波纳契数列(" + n +")"));
    }

    //generate 无限流
    //@Test
    public void test6() {
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
        IntStream ones = IntStream.generate(() -> 1);
        IntStream twos = IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        });
    }

    @Test
    public void test7() {
        Stream<String> stream = Stream.of("Java8", "Lambda", "In", "Action");
        //Optional<String> opt = stream.max(comparing(String::length));
        Optional<String> opt = stream.collect(maxBy(comparingInt(String::length)));
        opt.ifPresent(System.out::println);
    }
}
