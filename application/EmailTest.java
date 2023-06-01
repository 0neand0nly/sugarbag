package edu.handong.csee.se.sugarbag.plugin;

public class EmailTest {
    public static void main(String[] args) {
        EmailTest test = new EmailTest();
        test.emailTest();
    }

    private void emailTest() {
        // unitTest("10");
        unitTest("naver@handong.ac.kr");
        unitTest("kimdaesk@gmail.com");
        unitTest("abc@naver,");
        unitTest("abc");
    }

    private void unitTest(@Email String email) {
        System.out.println("This is what we want: " + email);
    }
}
