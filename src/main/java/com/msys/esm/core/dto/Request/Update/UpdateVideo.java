package com.msys.esm.core.dto.Request.Update;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdateVideo {

    @Id
    @NotBlank(message = "Video ID is required")
    @Size(max = 15, message = "Video ID cannot be longer than 15 characters")
    String videoId;

    String publishedAt;

    String thumbnail;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Playlist ID is required")
    String PlaylistId;
}
