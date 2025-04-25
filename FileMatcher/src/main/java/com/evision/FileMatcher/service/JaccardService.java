package com.evision.FileMatcher.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
// this class is used to calculate the matching score between two files
public class JaccardService {

    // main method
    public double calculateWeightedJaccardSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        //set of all the words present in both files
        Set<String> allWords = new HashSet<>(map1.keySet());

        // adding the second file words
        allWords.addAll(map2.keySet());

        // declaring min and max
        double min = 0;
        double max = 0;

        // loop through every word
        for (String word : allWords) {
            // holds the value of how many times this word was present in the first file
            int freq1 = map1.containsKey(word) ? map1.get(word) : 0;

            // holds the value of how many times this word was present in the second file
            int freq2 = map2.containsKey(word) ? map2.get(word) : 0;

            // Minimum frequency (intersection)
            min += Math.min(freq1, freq2);

            // Maximum frequency (union)
            max += Math.max(freq1, freq2);
        }

        // return 0 if max is 0
        if( max == 0)
            return 0;

        // return the score
        return min/max ;
    }
}
