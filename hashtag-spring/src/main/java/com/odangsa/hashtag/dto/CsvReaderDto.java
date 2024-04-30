package com.odangsa.hashtag.dto;

import com.odangsa.hashtag.domain.Category;
import com.odangsa.hashtag.domain.CategoryHashtag;
import com.odangsa.hashtag.domain.Hashtag;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
