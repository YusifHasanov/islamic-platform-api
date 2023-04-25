package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Update.UpdateArticle;
import com.msys.esm.Core.DTO.Response.ArticleResponse;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;

import java.util.Set;
import java.util.stream.Collectors;

public interface ArticleMapper {

    static ArticleResponse mapToArticleResponse(Article article, Set<Integer> categories) {

        ArticleResponse articleResponse = new ArticleResponse();

        articleResponse.setId(article.getId());
        articleResponse.setAuthor(article.getAuthor());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setPublishedAt(article.getPublishedAt());
        articleResponse.setCategories(categories);

        System.out.println(article.getCategories());

        return articleResponse;
    }

    static void mapToUpdateArticle(UpdateArticle article, Article updateArticle) {

        article.setTitle(updateArticle.getTitle());
        article.setContent(updateArticle.getContent());
        article.setPublishedAt(updateArticle.getPublishedAt());
        article.setAuthor_id(updateArticle.getAuthor().getId());
        article.setCategories(updateArticle.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

    }
}
