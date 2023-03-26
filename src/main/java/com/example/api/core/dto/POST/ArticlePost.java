package com.example.api.core.dto.POST;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class ArticlePost {
    @NotNull(message = "PublishedAt can not be empty")
    Date publishedAt;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Content is required")
    String content;
}
