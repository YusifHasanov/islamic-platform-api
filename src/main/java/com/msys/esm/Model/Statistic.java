package com.msys.esm.Model;

import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
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

    @Convert(converter = TrimValidator.class)
    @Column(name = "platform_name")
    String platformName;

    @Convert(converter = TrimValidator.class)
    @Column(name = "view_count")
    String viewCount;

    @NotNull
    @Column(name = "subscriber_count")
    @Convert(converter = TrimValidator.class)
    String subscriberCount;

    @Convert(converter = TrimValidator.class)
    @Column(name = "video_count")
    String videoCount;
}
