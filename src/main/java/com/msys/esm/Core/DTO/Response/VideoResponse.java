package com.msys.esm.Core.DTO.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoResponse {
    String videoId;
    String publishedAt;
    String thumbnail;
    String title;
    String playlistId;
}
