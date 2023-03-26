package com.example.api.core.dto.POST;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistPost {

    @NotBlank(message = "PlaylistId is required")
    @Size(max = 40, message = "PlaylistId can not be longer than 40 characters")
    String playlistId;
    @NotNull(message = "PublishedAt can not be null")
    Date publishedAt;

    @NotBlank(message = "Thumbnail is required")
    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;
}
