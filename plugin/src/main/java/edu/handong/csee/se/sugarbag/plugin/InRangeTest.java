package edu.handong.csee.se.sugarbag.plugin;

import edu.handong.csee.se.sugarbag.plugin.annotations.InRange;

public class InRangeTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        InRangeTest testIn = new InRangeTest();
        testIn.service(10);
        testIn.service(1000);
    }

    public void service(@InRange(min = 1, max = 100) int i) {
        System.out.println("i: " + i + " is in range!!");
    }
}