package com.mjc.school.service.dto;

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
public class AuthorDto {

    @Null(message = "Author id shouldn't be present")
    private Long id;

    @Size(min = 3, max = 15, message = "Name length must be between 3 and 15")
    private String name;

    @Null(message = "CreateDate shouldn't be populated")
    private LocalDateTime createDate;

    @Null(message = "LastUpdatedDate shouldn't be populated")
    private LocalDateTime lastUpdatedDate;
}
