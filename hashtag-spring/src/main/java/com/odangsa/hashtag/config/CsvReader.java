package com.odangsa.hashtag.config;

import com.odangsa.hashtag.dto.CsvReaderDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class CsvReader {
    @Bean
    public FlatFileItemReader<CsvReaderDto> csvScheduleReader(){
        /* 파일읽기 */
        FlatFileItemReader<CsvReaderDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("csv/classified.csv"));
        flatFileItemReader.setEncoding("UTF-8");

        /* defaultLineMapper : 읽으려는 데이터를 LineMapper를 통해 Dto로 매핑 */
        DefaultLineMapper<CsvReaderDto> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : csv 파일에서 구분자를 지정하고 구분한 데이터 setNames를 통해 각 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames("hashtag", "count", "categories");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : 매칭할 class 타입 지정 */
        BeanWrapperFieldSetMapper<CsvReaderDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(CsvReaderDto.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
