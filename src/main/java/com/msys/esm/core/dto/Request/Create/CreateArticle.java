package com.msys.esm.core.dto.Request.Create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateArticle {

    @NotNull(message = "PublishedAt can not be empty")
    String publishedAt;

    @NotBlank(message = "Title is required")
    String title;

    @NotBlank(message = "Content is required")
    String content;

    @NotNull(message = "Author is required")
    int author_id;

    @NotNull(message = "Categories is required")
    Set<Integer> categories;

}
