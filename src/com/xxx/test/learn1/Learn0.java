package com.xxx.test.learn1;

import org.junit.Test;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class Learn0 {
    private static List<Transaction> transactions;
    static {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //@Test
    public void test0() {
        List<Transaction> list =
                transactions.stream()
                    .filter(t -> t.getYear() == 2011)
                    .sorted(comparing((Transaction::getValue)))
                    .collect(toList());
        System.out.println(list);
    }

    //@Test
    public void teset1() {
        List<String> list0 =
                transactions.stream()
                    .map(t -> t.getTrader().getCity())
                    .distinct()
                    .collect(toList());

        Set<String> set0 =
                transactions.stream()
                        .map(t -> t.getTrader().getCity())
                        //.distinct()
                        .collect(toSet());
        System.out.println(list0);
        System.out.println(set0);
    }

    //@Test
    public void test2() {
        List<Trader> traders =
                transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println(traders);
    }

    //@Test
    public void test3() {
        List<String> list0 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                //.sorted(comparing(String::toString))
                .sorted()
                .collect(toList());
        String  str0 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                //.sorted(comparing(String::toString))
                .sorted()
                .reduce("",(a,b)-> a+b+",");
        //joining()使用了StringBuffer，可以更少的去创建string对象，节约内存。
        //joining会调用对象的toString方法，所以如果可以的话，可以将对象的toString更改，这样可能可以稍微节省一点步骤
        String  str1 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                //.sorted(comparing(String::toString))
                .sorted()
                .collect(joining());
        System.out.println(list0);
        System.out.println(str0);
        System.out.println(str1);
    }

    //@Test
    public void test4() {
        boolean flag = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(flag);
    }

    //@Test
    public void test5() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        Integer money = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        System.out.println(money);
    }

    //@Test
    public void test6() {
        Optional<Integer> opt = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        opt.ifPresent(System.out::print);
    }

    //@Test
    public void test7() {
        Optional<Transaction> opt0 = transactions.stream()
                .reduce((t1, t2) -> {
                    if (t1.getValue() < t2.getValue()) return t1;
                    return t2;
                });
        Optional<Transaction> opt1 = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

        Optional<Transaction> opt2 = transactions.stream()
                .min(comparing(Transaction::getValue));

        opt0.ifPresent(System.out::println);
        opt1.ifPresent(System.out::println);
        opt2.ifPresent(System.out::println);
    }



}
