package com.msys.esm.Model;

import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "videos")
public class Video  {

    @Id
    @Convert(converter = TrimValidator.class)
    @Column(name = "video_id")
    String videoId;

    @Convert(converter = TrimValidator.class)
    @Column(name = "published_at")
    String publishedAt;

    @Convert(converter = TrimValidator.class)
    @Column(name = "thumbnail")
    String thumbnail;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "title")
    String title;

    @Convert(converter = TrimValidator.class)
    @JoinColumn(name = "playlist_id")
    String playlistId;

}