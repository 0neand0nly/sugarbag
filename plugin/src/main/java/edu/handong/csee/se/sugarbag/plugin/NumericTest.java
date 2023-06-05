package edu.handong.csee.se.sugarbag.plugin;

import edu.handong.csee.se.sugarbag.plugin.annotations.Numeric;

public class NumericTest {
    public static void main(String[] args) {
        NumericTest test = new NumericTest();
        test.numericTest();
    }

    private void numericTest() {
        // unitTest("10");
        unitTest("7.0");
        unitTest("11");
        unitTest("1");
        unitTest("abc");
    }

    private void unitTest(@Numeric(max = 0, min = 9, numericType = "double") String num) {
        System.out.println("This is what we want: " + num);
    }
}
