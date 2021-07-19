package ru.pipko.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Scanner;

@Service
public class ReadServiceImpl implements ReadService {

    private final Scanner scanner;

    public ReadServiceImpl(@Value("#{T(java.lang.System).in}") InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}
