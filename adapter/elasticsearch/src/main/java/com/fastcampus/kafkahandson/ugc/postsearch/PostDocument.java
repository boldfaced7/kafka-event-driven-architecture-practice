package com.fastcampus.kafkahandson.ugc.postsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(indexName = "post-1")
@NoArgsConstructor
@AllArgsConstructor
public class PostDocument {

    @Id
    private Long id;

    @Field(
            type = FieldType.Text,
            analyzer = "english"
    )
    private String title;

    @Field(
            type = FieldType.Text,
            analyzer = "english"
    )
    private String content;

    @Field(
            type = FieldType.Keyword
    )
    private String categoryName;

    @Field(
            type = FieldType.Keyword
    )
    private List<String> tags;
    @Field(
            type = FieldType.Date,
            format = DateFormat.date_hour_minute_second_millis,
            pattern = "date_optional_time||epoch_millis"
    )
    private LocalDateTime indexedAt;
}
