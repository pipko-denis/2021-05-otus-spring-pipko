package ru.pipko.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PrintStreamService implements PrintService {

    private final PrintStream printStream;

    public PrintStreamService(@Value("#{T(java.lang.System).out}") PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printLn(String line) {
        printStream.println(line);
    }

}
