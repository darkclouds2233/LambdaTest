package com.xxx.test.learn0;

import org.junit.Test;

public class Test0 {

    public void test000(int a,ITest iTest) {
        if (iTest.test(a)) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    public void  test333(Integer a,Integer b,ITest1<Integer> iTest1) {
        if (iTest1.test(a,b)) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    @Test
    public void test111(){
        //lambda。。
        test000(99,a -> a>100);
        test000(99,(int a) -> a>100);
        test000(99,(int a) -> { a++; return a>100;});
        test333(99,98,(a,b) -> a>100||b>100);
        test333(99,98,(Integer a,Integer b) -> a>100||b>100);
        test333(99,98,(Integer a,Integer b) -> {a--; return a>100||b>100;});

        //匿名内部类
        test000(101, new ITest() {
            @Override
            public boolean test(int a) {
                return a>100;
            }
        });
    }

    public void test123(int a) {
        if (a>100) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }
}
