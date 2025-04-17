package com.task.reader;

import com.task.model.FileRecord;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
/*
* This Class takes a path of directory and creates a list of all files presented
* creates an iterator to loop through all files one by one
* every file would go through all three steps at a time
* FileRecord -> item reader -> item processor -> item writer
* the reader will end once it there are no more files
* *** NOTE: i have made an assumption that the directory would only have .txt files ****
* *** I can filter out only .txt files ****
* *** but for just following the requirements needed I skipped it ***
* */
@Slf4j
@Component
@NoArgsConstructor
public class FileItemReaderV2 implements ItemReader<FileRecord> {

    private Iterator<File> fileIterator;
    // inject the path
    @Value("${directory.path}")
    private String dirPath;

    /* Post Construct is used to make sure that the path is being used properly
     * after creating the bean
     * I can also just go through files without creating an iterator with and index,
     * but I have used iterator for a cleaner code
     * */
    @PostConstruct
    public void ProcessDirectoryIntoList() {
        // reading the directory path
        File directory = new File(dirPath);
        // creating an array of files for all the files in the directory
        File[] files = directory.listFiles();

        // creating an iterator for looping
        if (files != null) {
            fileIterator = Arrays.asList(files).iterator();
        } else {
            // Empty iterator if directory is invalid or empty
            fileIterator = Collections.emptyIterator();
        }
    }



    @Override
    public FileRecord read() throws Exception {
        if (!fileIterator.hasNext()) {
            log.info("End Of Reading ...");
            return null; // End of reading
        }
        File file = fileIterator.next();
        log.info("Reading file {}", file.getName());
        // read the file
        String text = new String(Files.readAllBytes(file.toPath()));

        // pass it to the processor as FileRecord
        return new FileRecord(file.getName(),text,0);
    }
}
