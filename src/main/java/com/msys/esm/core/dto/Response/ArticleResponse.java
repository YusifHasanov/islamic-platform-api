package com.msys.esm.core.dto.Response;

import com.msys.esm.entities.Author;
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
public class ArticleResponse {
    int id;
    String publishedAt;
    String title;
    String content;
    Author author;
    Set<Integer> categories;
}
