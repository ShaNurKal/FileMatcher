package com.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;


/*
* this Class would be used to hold the attributes of a file
* throughout the whole process of the step
*/
@Data
@AllArgsConstructor
public class FileRecord {
      private String name;
      private String text;
      private double score;
}
