package com.msys.esm.Core.DTO.Request.Create;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateQuestion {

    @NotBlank(message = "Question is required")
    String question;

    @NotBlank(message = "Answer is required")
    String answer;
}
