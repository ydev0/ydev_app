package com.ydev00.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class MarkdownHandler {
  public String toString(FileReader reader) {
    String markdown;
    try {
      BufferedReader br = new BufferedReader(reader);

      StringBuilder stringBuilder = new StringBuilder();
      char[] buffer = new char[10];

      while (br.read(buffer) != -1) {
        stringBuilder.append(new String(buffer));
        buffer = new char[10];
      }

      markdown = stringBuilder.toString();
      br.close();
    } catch (Exception e) {
      System.err.println("Could not read markdown file: " + e.getMessage());
      return null;
    }
    return markdown;
  }
}
