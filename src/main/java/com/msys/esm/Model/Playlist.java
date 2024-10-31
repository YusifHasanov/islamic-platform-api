package com.msys.esm.Model;
import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "playlists")
public class Playlist  {

    @Id
    @NotBlank  @Size(max = 40)
    @Convert(converter = TrimValidator.class)
    @Column(name = "playlist_id")
    String playlistId;

    @Column(name = "published_at")
    @Convert(converter = TrimValidator.class)
    String publishedAt;

    @Column(name = "thumbnail")
    @Convert(converter = TrimValidator.class)
    String thumbnail;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "title")
    String title;

    @Column(name = "video_count")
    Integer videoCount;
}
