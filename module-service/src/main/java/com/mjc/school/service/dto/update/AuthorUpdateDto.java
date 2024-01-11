package com.mjc.school.service.dto.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorUpdateDto {

    @NotNull(message = "Author id should be present")
    private Long id;

    @Size(min = 3, max = 15, message = "Name length must be between 3 and 15")
    private String name;
}
