package com.evision.FileMatcher.service;

import com.evision.FileMatcher.exception.CustomException;
import com.evision.FileMatcher.model.FileRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
// Main service to Match a file to a pool of files
@Service
public class FileService {
    // jaccardService is needed for calculating the score based on the algorithm
    final private JaccardService jaccardService;
    // mapService is for generating a map words and their frequencies out of a file
    final private MapService mapService;

    // Constructor for dependency injection
    public FileService(MapService mapService, JaccardService jaccardService) {
        this.mapService = mapService;
        this.jaccardService = jaccardService;
    }

    // Main method to match a file with other files in a directory
    // return list of File record (file names with their scores )
    public List<FileRecord> fileMatcher(String filePath , String directoryPath ) throws CustomException {
        // Read the file and generate a frequency map
        Map<String, Integer> givenFileMap = fileReader(filePath);

        // Read files from the specified directory
        File [] listOfFiles = directoryReader(directoryPath);

        // Calculate scores for each file and put it into a list
        List<FileRecord> resultList = calculateScore(givenFileMap, listOfFiles);
        return resultList;
    }

    // Reads the content of a file and converts it into a map of word frequencies
    public Map<String, Integer> fileReader(String filePath) throws CustomException {
        try {
            // Read file content as a string
            String fileContent = Files.readString(Path.of(filePath));

            // Generate a map of word frequencies using MapService
            Map<String, Integer> FileMap = mapService.mapFunction(fileContent);

            // Return the map
            return FileMap;
        }
        catch (Exception e) {
            // Throw a custom exception if an error occurs
            throw new CustomException("the file path is incorrect");
        }
    }

    // Reads files from the specified directory
    public File[] directoryReader(String directoryPath) throws CustomException {
        // Create a File object for the directory path
        File directory = new File(directoryPath);

        // Check if the directory exists and is valid
        if (!directory.exists() || !directory.isDirectory()) {
            throw new CustomException("The specified path is not a directory or does not exist");
        }
        // List all files in the directory
        File[] files = directory.listFiles();

        // Check if the directory is empty
        if (files.length == 0 ) {
            throw new CustomException("there's no files in this directory");
        }
        // Return the list of files
        return files;
    }

    // Calculates the similarity score of the given file with each file in the directory
    List <FileRecord> calculateScore (Map<String, Integer> givenFileMap, File[] listOfFiles) throws CustomException {
        // Initialize a list to hold the results
        List <FileRecord> resultList = new LinkedList<>();

        // Iterate through each file in the directory
        for (File file : listOfFiles) {

            // Check if it's a regular file
            if (file.isFile())
            {
                // Create a new FileRecord object to hold values ( file name and the score of it)
                FileRecord fileRecord = new FileRecord();

                // Set the file name
                fileRecord.setFileName(file.getName());

                // Read the file content and generate a map
                Map<String,Integer> currentDirectoryFileMap = fileReader(file.getAbsolutePath());

                // Calculate the Jaccard similarity score
                double score = jaccardService.calculateWeightedJaccardSimilarity(currentDirectoryFileMap, givenFileMap) * 100;

                // Set the similarity score
                fileRecord.setScore(score);

                // Add the record to the result list
                resultList.add(fileRecord);
            }
        }
        // Return the list of results
        return resultList;
    }

}
