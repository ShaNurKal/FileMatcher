package com.evision.FileMatcher.controller;


import com.evision.FileMatcher.exception.CustomException;
import com.evision.FileMatcher.model.FileRecord;
import com.evision.FileMatcher.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api/fileMatcher")
public class FileController {
    // declaring the fileService which we will need to do all the business logic
    final private FileService fileService;

    // injecting dependency
    FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // endpoint that takes two params the file path and the directory path
    // return list of File record (file names with their scores )
    // makes sure you are using forward slash when passing the paths
    @PostMapping("/calculate")
    public ResponseEntity<List<FileRecord>> FileMatcher(
            @RequestParam("filePath") String filePath,
            @RequestParam("directoryPath") String directoryPath) throws CustomException {

      // calling the fileMatcher to calculate all the scores and saving it into result
      List<FileRecord> result = fileService.fileMatcher(filePath,directoryPath);

      // returning the result and the status code of 200
      return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
