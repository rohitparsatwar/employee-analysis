package org.bigcompany.util;

import java.util.List;

public class OutputPrinter {

    private OutputPrinter() {

    }

    public static void display(List<String> outputList) {
        outputList.forEach(System.out::println);
        System.out.println();
    }
}
