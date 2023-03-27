package com.example.api.core.dto.Request.Create;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateVideo {

    @NotBlank(message = "Video ID is required")
    @Size(max = 15, message = "Video ID cannot be longer than 15 characters")
    String videoId;

    Date publishedAt;

    @NotBlank(message = "Thumbnail is required")
    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;

}
