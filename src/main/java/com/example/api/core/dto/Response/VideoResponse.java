package com.example.api.core.dto.Response;
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
public class VideoResponse {
    int id;
    String videoId;
    Date publishedAt;
    String thumbnail;
    String title;
    String PlaylistId;
}
