package com.msys.esm.core.dto.Response;

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
public class PlaylistResponse {
    String playlistId;
    String publishedAt;
    String thumbnail;
    String title;
    int videoCount;
}
