package com.msys.esm.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "playlists")
public class Playlist  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank  @Size(max = 40)
    @Column(name = "playlist_id")
    String playlistId;

    @Column(name = "published_at")
    Date publishedAt;
    @NotBlank
    @Column(name = "thumbnail")
    String thumbnail;
    @NotBlank
    @Column(name = "title")
    String title;
    @OneToMany (mappedBy = "playlist")
    List<Video> videos;
}
