package edu.handong.csee.se.sugarbag.plugin;

import edu.handong.csee.se.sugarbag.plugin.annotations.Negative;

public class NeutralTest {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        NegativeTest testNeg = new NegativeTest();
        testNeg.service(0);
        testNeg.service(1);
    }

    public void service(@Negative int i) {
        System.out.println("i: " + i + " is zero!!");
    }
}