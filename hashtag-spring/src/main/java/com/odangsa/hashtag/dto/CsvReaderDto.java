package com.odangsa.hashtag.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CsvReaderDto {
    private String hashtag;
    private String count;
    private String categories;
}
