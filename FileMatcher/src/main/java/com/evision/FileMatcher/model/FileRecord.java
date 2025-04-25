package com.evision.FileMatcher.model;

// a model for holding values for the output ( file name and the score)
public class FileRecord {
    private String fileName;
    private double score;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
