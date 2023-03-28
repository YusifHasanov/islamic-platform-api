package com.msys.esm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Statistic  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    @NotNull
    @Column(name = "view_count")
    String viewCount;
    @NotNull
    @Column(name = "subscriber_count")
    String subscriberCount;

    @Column(name = "hidden_subscriber")
    boolean hiddenSubscriber;
    @NotNull
    @Column(name = "video_count")
    String videoCount;
}
