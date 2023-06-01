package edu.handong.csee.se.sugarbag.plugin;

public class TestNeut {
    public static void main(String[] args) {
        TestNeut testNeut = new TestNeut();
        testNeut.service(0);
        testNeut.service(1);
    }

    public void service(@Neutral int i) {
        System.out.println("i: " + i + " is zero!!");
    }
}