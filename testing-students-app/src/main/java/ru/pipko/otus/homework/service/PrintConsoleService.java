package ru.pipko.otus.homework.service;

import java.io.PrintStream;

public class PrintConsoleService implements PrintService{

    private final PrintStream printStream;

    public PrintConsoleService(){
        this.printStream = System.out;
    }

    public void printLn(String line) {
        if ( line == null ) {
            printStream.println("The row that you try to display is null");
            return;
        }
        if ( line.isBlank() ) {
            printStream.println("The row that you try to display is blank or empty or contains only whitespaces");
            return;
        }
        printStream.println(line);
    }

}
