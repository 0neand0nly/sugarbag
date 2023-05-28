package edu.handong.csee.se.sugarbag.plugin;

public class NumericTest {
    public static void main(String[] args) {
        NumericTest test = new NumericTest();
        test.numericTest();
    }

    private void numericTest() {
        // unitTest("10");
        unitTest(7);
        unitTest(11);
        unitTest(1);
        // unitTest("abc");
    }

    private void unitTest(@Numeric(min = 5, max = 10, numericType = "int") int number) {
        System.out.println("This is what we want: " + number);
    }
}
