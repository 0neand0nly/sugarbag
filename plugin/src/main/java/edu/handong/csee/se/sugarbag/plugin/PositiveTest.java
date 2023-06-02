package edu.handong.csee.se.sugarbag.plugin;

public class PositiveTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        PositiveTest testPos = new PositiveTest();
        testPos.service(1);
    }

    public void service(@Positive int i) {
        System.out.println("i: " + i + " is positive!!");
    }
}