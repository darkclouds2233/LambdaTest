package com.xxx.test.learn3;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test0 {
    //滥用reduce，虽然可以完成toList功能，但是很不直观，而且将List自己去声明，而不是交给stream，并行运行可能会出现问题
    @Test
    public void test0() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        List<Integer> numbers = stream.reduce(
                new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l; },
                        (List<Integer> l1, List<Integer> l2) -> {
            l1.addAll(l2);
            return l1; });
        System.out.println(numbers);
    }
}
