package com.evision.FileMatcher.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MapService {
    // turn a String into a List of lowercase words and filtering out all non-alphabetic words
    public List<String> normalizeFunction(String file) {
        return Arrays.stream(file.toLowerCase().split("\\s+"))  // turn all text into lower case and split words based on spaces
                .filter(word -> word.matches("^[a-zA-Z]+$")) // Filter out words with numbers or special characters
                .collect(Collectors.toList()); // collect it into a list
    }

    // Build frequency map for a file for every word with its number of occurrence
    public Map<String, Integer> mapFunction(String file) {

        // make a file into a list of filtered lowercase words
        List<String> StringOfWords = normalizeFunction(file);

        // assign a map to hold every word with the frequencies
        Map<String, Integer> fileMap = new HashMap<>();

        // loop through every word
        for (String word : StringOfWords) {
            // if a word exists increasing the frequency by 1
            if (fileMap.containsKey(word)) {
                fileMap.put(word, fileMap.get(word) + 1);
            }
            // if a word doesn't exist put it into a map with a frequency of 1
            else {
                fileMap.put(word, 1);
            }
        }
        // return the map of frequencies
        return fileMap;
    }
}
