package com.example.api.core.dto.POST;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AuthorPost {


    @NotBlank(message = "Name is required" )
    @Size(max = 35, message = "Name is should be less than 35 characters")
    String name;


    @NotBlank (message = "Image is required")
    String image;
}
