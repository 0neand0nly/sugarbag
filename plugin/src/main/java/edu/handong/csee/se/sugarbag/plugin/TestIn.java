package edu.handong.csee.se.sugarbag.plugin;

public class TestIn {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        TestIn testIn = new TestIn();
        testIn.service(10);
        testIn.service(1000);
    }

    public void service(@InRange(min = 1, max = 100) int i) {
        System.out.println("i: " + i + " is in range!!");
    }
}