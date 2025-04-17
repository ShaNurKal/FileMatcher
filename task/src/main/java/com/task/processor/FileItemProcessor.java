package com.task.processor;

import com.task.model.FileRecord;
import com.task.service.CompareService;
import com.task.service.MapService;
import com.task.service.TargetFileLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
/*
*   after receiving a file as a String from FileItemReader
*   it then creates a map out of it
*   and calculates the matching score and pass it to ScoreItemWriter
* */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileItemProcessor implements ItemProcessor<FileRecord, FileRecord> {

    private final CompareService compareService;
    private final MapService mapService;
    private final TargetFileLoader targetFileLoader;

    @Override
    public FileRecord process(FileRecord file) throws IOException {
        try {
            // get String of text
            String currentDirFile = file.getText();
            log.info("translating file {} to a map  ...", file.getName());
            // turn the String into a map of words and their frequencies
            Map<String, Integer> currentFileMap = mapService.mapFunction(currentDirFile);
            log.info( "translation was done ");
            // retrieve the map of the target file
            Map<String, Integer> targetFileMap = targetFileLoader.getTargetFileMap();
            log.info( "evaluation Match score ...");
            // evaluate the matching  Score
             double result = compareService.calculateWeightedJaccardSimilarity(currentFileMap, targetFileMap) * 100;
            log.info("Matching score is {}", result);
            file.setScore(result);


        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + file.getName(), e);
        }
        // pass it to File Item Writer
        return file;
    }

}