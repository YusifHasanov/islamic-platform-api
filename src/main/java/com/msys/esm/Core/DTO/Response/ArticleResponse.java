package com.msys.esm.Core.DTO.Response;

import com.msys.esm.Model.Author;
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
