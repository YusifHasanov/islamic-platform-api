package com.msys.esm.Core.DTO.Request.Update;

import jakarta.persistence.Id;
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
public class UpdatePlaylist {

    @Id
    @NotBlank(message = "PlaylistId is required")
    @Size(max = 40, message = "PlaylistId can not be longer than 40 characters")
    String playlistId;
    @NotNull(message = "PublishedAt can not be null")
    String publishedAt;

    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;
    int videoCount;

}
