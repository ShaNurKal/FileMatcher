package com.task.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
  this is a simple class that takes a text and normalize it
  normalization turns it into a List of case-insensitive words
  filters non-alphabetic words leaving only full alphabetic words
  then it creates a map of words with its frequency
  ******* returns map of words with its frequency ******
  */
@Service
public class MapService {
    // turn a String into a List of case-insensitive words and filtering out all non-alphabetic words
    public List<String> normalizeFunction(String file) {
        return Arrays.stream(file.toLowerCase().split("\\s+"))  // turn all text into lower case and split words based on spaces
                .filter(word -> word.matches("^[a-zA-Z]+$")) // Filter out words with numbers or special characters
                .collect(Collectors.toList());
    }

    // Build frequency map for a file for every word with its number of occurrence
    public Map<String, Integer> mapFunction(String file) {
        List<String> words = normalizeFunction(file);
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }
}
