package com.task.service;
import org.springframework.stereotype.Service;
import java.util.*;

/* this Class would be used to Calculate the Similarity function
    it uses WeightedJaccardSimilarityAlgorithm
    it takes two maps( a map holds every word in a file with its Frequency) as inputs
    creates a set of all the words present in both maps
    then iterates over the set word by word
    frequency 1 holds the value of how many times this word was present in the first file
    frequency 2 holds the value of how many times this word was present in the second file
    accumulates the min for all words frequency
    accumulates the max for all words frequency
    *** returns min/max as a score from 0 to 1 *****
    this score represents the ratio between the intersection and union
    for the whole set of words present in both files
*/
@Service
public class CompareService {

    public double calculateWeightedJaccardSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        //set of all the words present in both files
        Set<String> allWords = new HashSet<>(map1.keySet());
        allWords.addAll(map2.keySet());

        double min = 0;
        double max = 0;

        for (String word : allWords) {
            // holds the value of how many times this word was present in the first file
            int freq1 = map1.containsKey(word) ? map1.get(word) : 0;// map1.getOrDefault(word, 0);

            // holds the value of how many times this word was present in the second file
            int freq2 = map2.containsKey(word) ? map2.get(word) : 0;//map2.getOrDefault(word, 0);

            min += Math.min(freq1, freq2); // Minimum frequency contributes to intersection
            max += Math.max(freq1, freq2); // Maximum frequency contributes to union
        }

        return max == 0 ? 0 : min / max;
    }

}
