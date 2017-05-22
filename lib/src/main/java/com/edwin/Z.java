package com.edwin;

public class Z {

    private static String aa = "a";

    public static void main(String[] args) {
        System.out.println(Test.a);
        aa = "aa";
        System.out.println(Test.a);
    }

    public static class Test {
        public static final String a = getString();

        public static String getString() {
            return aa;
        }
    }

}
