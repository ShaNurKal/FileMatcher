package com.task.config;


import com.task.model.FileRecord;
import com.task.processor.FileItemProcessor;
import com.task.writer.ScoreItemWriter;
import com.task.reader.FileItemReaderV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Slf4j
@RequiredArgsConstructor
/*
* this is Batch Config file I injected the processor and writer and reader
* created a Step and job to be executed automatically
* Note: the job can be executed manually through a REST end-point also
* */
public class Config {

    private final FileItemProcessor processor;
    private final ScoreItemWriter writer;
    private final FileItemReaderV2 reader;

    @Bean
     public Step step(JobRepository jobRepository,PlatformTransactionManager manager) {
          return new StepBuilder("step", jobRepository)
                  .<FileRecord, FileRecord> chunk(1,manager)
                  .reader(reader)
                  .processor(processor)
                  .writer(writer)
                  .build();
     }

    @Bean
    public Job job(JobRepository jobRepository,Step step) {
         return new JobBuilder("job",jobRepository)
                 .start(step).build();
    }

}
