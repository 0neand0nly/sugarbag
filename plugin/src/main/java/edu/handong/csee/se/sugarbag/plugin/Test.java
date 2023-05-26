package edu.handong.csee.se.sugarbag.plugin;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Test test = new Test();
        test.service(1);
    }

    public void service(@Positive int i) {
        System.out.println("i: " + i + " is positive!!");
    }
}
