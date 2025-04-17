package com.task.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
/*
*  this class is responsible for processing the given file
*  that we would compare to the pool of files
*  it takes the path of the target file
*  and normalize it and then
*  **** returns a map of words and frequencies ******
* */
@Component
@RequiredArgsConstructor
public class TargetFileLoader {

    final private MapService mapService;
    // map for the target file
    private Map<String, Integer> targetFileMap;
    // injecting path
    @Value("${target.file.path}")
    private  String targetFilePath;

    /* here also I have used PostConstruct
     * to make sure that after creating the bean and injecting dependencies
     * that the path is being passed properly
     *
     * */
    @PostConstruct
    public void processFileIntoMap() throws IOException {
        String TargetFile = new String(Files.readAllBytes(Paths.get(targetFilePath)));
        this.targetFileMap = mapService.mapFunction(TargetFile);
    }
    //
    public Map<String, Integer> getTargetFileMap() throws IOException {
        return targetFileMap;
    }
}
