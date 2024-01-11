package com.mjc.school.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {

    @Null(message = "News id shouldn't be present")
    private Long id;

    @Size(min = 5,max = 30,message = "Title length must be between 5 and 30")
    private String title;

    @Size(min = 5,max = 255,message = "Content length must be between 5 and 255")
    private String content;

    @Null(message = "CreateDate shouldn't be populated")
    private LocalDateTime createDate;

    @Null(message = "LastUpdatedDate shouldn't be populated")
    private LocalDateTime lastUpdatedDate;

    @NotNull(message = "Author id should be present")
    private Long authorId;

    private List<Long> tagsId;

    @Null(message = "Comments id shouldn't be present")
    private List<Long> commentsId;
}
