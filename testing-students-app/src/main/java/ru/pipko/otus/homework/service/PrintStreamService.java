package ru.pipko.otus.homework.service;

import java.io.PrintStream;

public class PrintStreamService implements PrintService{

    private final PrintStream printStream;

    public PrintStreamService(){
        this.printStream = System.out;
    }

    public void printLn(String line) {
        printStream.println(line);
    }

}
