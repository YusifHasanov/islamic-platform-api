package com.example.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "videos")
public class Video  {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "video_id")
    String videoId;
    @Column(name = "published_at")
    Date publishedAt;
    @Column(name = "thumbnail")
    String thumbnail;
    @Column(name = "title")
    String title;
}