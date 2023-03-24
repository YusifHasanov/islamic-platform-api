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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "statistics")
public class Statistic {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "view_count")
    String viewCount;
    @Column(name = "subscriber_count")
    String subscriberCount;
    @Column(name = "hidden_subscriber")
    boolean hiddenSubscriber;
    @Column(name = "video_count")
    String videoCount;
}
