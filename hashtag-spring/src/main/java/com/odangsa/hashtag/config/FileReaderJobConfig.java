package com.odangsa.hashtag.config;

import com.odangsa.hashtag.dto.CsvReaderDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FileReaderJobConfig {

//    private static final int chunkSize = 5999; //데이터 처리할 row size
//    private final CsvReader csvReader;
//    private final CsvDataWriter csvDataWriter;
//    private final CsvRelationWriter csvRelationWriter;
//
//    @Bean
//    public Job myJob(JobRepository jobRepository, Step step1, Step step2){
//        return new JobBuilder("myJob", jobRepository)
//                .start(step1)
//                .next(step2)
//                .build();
//    }
//
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        return new StepBuilder("step1", jobRepository)
//                .<CsvReaderDto,CsvReaderDto>chunk(chunkSize, transactionManager)
//                .reader(csvReader.csvScheduleReader())
//                .writer(csvDataWriter)
//                .build();
//    }
//
//    @Bean
//    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        return new StepBuilder("step2", jobRepository)
//                .<CsvReaderDto,CsvReaderDto>chunk(chunkSize, transactionManager)
//                .reader(csvReader.csvScheduleReader())
//                .writer(csvRelationWriter)
//                .build();
//    }
}
