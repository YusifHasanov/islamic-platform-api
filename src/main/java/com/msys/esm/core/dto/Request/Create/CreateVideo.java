package com.msys.esm.core.dto.Request.Create;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateVideo {

    @NotBlank(message = "Video ID is required")
    @Size(max = 15, message = "Video ID cannot be longer than 15 characters")
    String videoId;

    String publishedAt;

    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Playlist ID is required")
    String playlistId;

}
