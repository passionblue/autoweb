package com.intv;

public class StaticClassTest {

    public static void main(String[] args) {

        A.doThis();
        B.doThis();
    }
}

class A {
    public static void doThis(){
        System.out.println("A");
    }
}
class B {
    public static void doThis(){
        System.out.println("B");
    }
}
