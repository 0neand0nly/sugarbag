package edu.handong.csee.se.sugarbag.plugin;

public class StringMatchTest {
    public static void main(String[] args) {
        StringMatchTest stringMatchTest = new StringMatchTest();
        stringMatchTest.stringFormatTest();
    }

    private void stringFormatTest() {
        unitTest("2023-01-01"); // valid date
        unitTest("20230101"); // invalid date
        unitTest("2023-13-01"); // invalid date
        unitTest("2023-01-32"); // invalid date
        // unitTest("2023-02-28"); // valid date
    }

    private void unitTest(@StringMatch(format = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])") String date) {
        System.out.println("This is what we want: " + date);
    }
}
