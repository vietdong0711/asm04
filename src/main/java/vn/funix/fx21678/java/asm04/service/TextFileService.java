package vn.funix.fx21678.java.asm04.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        List<List<String>> rs = new ArrayList<>();

        String line = bufferedReader.readLine();
        while (line != null) {
            List<String> strings = Arrays.asList(line.split(COMMA_DELIMITER));
            rs.add(strings);
            line = bufferedReader.readLine();
        }
        return rs;
    }
}
