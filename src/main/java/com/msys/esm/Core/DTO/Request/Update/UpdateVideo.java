package com.msys.esm.Core.DTO.Request.Update;

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
public class UpdateVideo {

    String publishedAt;

    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Playlist ID is required")
    String playlistId;
}
