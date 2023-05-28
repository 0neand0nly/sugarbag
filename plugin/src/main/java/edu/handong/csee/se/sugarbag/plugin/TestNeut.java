package edu.handong.csee.se.sugarbag.plugin;

public class TestNeut {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        TestNeg testNeg = new TestNeg();
        testNeg.service(0);
        testNeg.service(1);
    }

    public void service(@Negative int i) {
        System.out.println("i: " + i + " is zero!!");
    }
}
