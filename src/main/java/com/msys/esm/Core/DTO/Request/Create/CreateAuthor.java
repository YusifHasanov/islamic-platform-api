package com.msys.esm.Core.DTO.Request.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAuthor {


    @NotBlank(message = "Name is required" )
    @Size(max = 35, message = "Name is should be less than 35 characters")
    String name;


    @NotBlank (message = "Image is required")
    String image;
}
