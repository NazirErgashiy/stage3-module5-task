package com.mjc.school.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @Null(message = "Author id shouldn't be present")
    private Long id;

    @Size(min = 5, max = 255, message = "Content length must be between 5 and 255")
    private String content;

    @Null(message = "CreateDate shouldn't be populated")
    private LocalDateTime createDate;

    @Null(message = "LastUpdatedDate shouldn't be populated")
    private LocalDateTime lastUpdatedDate;

    @NotNull(message = "News id should be present")
    private Long newsId;
}
