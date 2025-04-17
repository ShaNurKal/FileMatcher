package com.task.writer;
import com.task.model.FileRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileWriter;

/*
ItemWriter only job is to append to a file the name and the matching score of passed file
it uses a chunk based mechanism to write into the file
but since we set the chunk as one in the job
it will write only one file at a time
* */
@Component
@Slf4j
public class ScoreItemWriter implements ItemWriter<FileRecord> {
    //passing the path of the output file
    @Value("${output.file.path}")
    private String outputPath ;

    @Override
    public void write(Chunk<? extends FileRecord> items) throws Exception {
        //opens the file and append into it
        try (FileWriter writer = new FileWriter(outputPath,true)) {
            // ( items only has one value)
            // it will write it out to the file
            for (FileRecord file : items) {
                log.info("Writing scores {} of file {}" , file.getScore(), file.getName());
                writer.write("File name: "+file.getName() + " Matching score is: " + file.getScore() + "% \n");
            }
        }

    }
}