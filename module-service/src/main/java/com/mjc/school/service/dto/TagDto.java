package com.mjc.school.service.dto;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    @Null(message = "Tag id shouldn't be present")
    private Long id;

    @Size(min = 3,max = 15,message = "Name length must be between 3 and 15")
    private String name;
}
