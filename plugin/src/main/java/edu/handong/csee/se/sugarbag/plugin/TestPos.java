package edu.handong.csee.se.sugarbag.plugin;

public class TestPos {
    public static void main(String[] args) {
        TestPos testPos = new TestPos();
        testPos.service(1);
        testPos.service(-1);
    }

    public void service(@Positive int i) {
        System.out.println("i: " + i + " is positive!!");
    }
}