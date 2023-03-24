package com.example.api.entities;
import jakarta.persistence.*;
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
public class Playlist {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "playlist_id")
    String playlistId;
    @Column(name = "published_at")
    Date publishedAt;
    @Column(name = "thumbnail")
    String thumbnail;
    @Column(name = "title")
    String title;
    @OneToMany (mappedBy = "playlist")
    List<Video> videos;
}
