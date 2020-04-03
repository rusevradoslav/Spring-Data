package com.example.car_dealer_system.utils;

import java.io.IOException;

public interface FileUtil {
    String readFileContinent(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;
}
