package com.mjc.school.service.dto.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateDto {

    @NotNull(message = "News id should be present")
    private Long id;

    @Size(min = 5, max = 30, message = "Title length must be between 5 and 30")
    private String title;

    @Size(min = 5, max = 255, message = "Content length must be between 5 and 255")
    private String content;

    private List<Long> tagsId;
}
