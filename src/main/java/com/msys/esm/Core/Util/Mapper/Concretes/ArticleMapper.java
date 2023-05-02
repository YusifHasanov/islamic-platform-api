package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.DTO.Request.Create.CreateArticle;
import com.msys.esm.Core.DTO.Request.Update.UpdateArticle;
import com.msys.esm.Core.DTO.Response.ArticleResponse;
import com.msys.esm.Core.Util.Exceptions.ArticleAlreadyExsitsException;
import com.msys.esm.Core.Util.Exceptions.ArticleNotFoundException;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.Mapper.Abstracts.IArticleMapper;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;
import com.msys.esm.Repository.ArticleRepository;
import com.msys.esm.Repository.AuthorRepository;
import com.msys.esm.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ArticleMapper implements IArticleMapper {

    ArticleRepository articleRepository;
    CategoryRepository categoryRepository;
    AuthorRepository authorRepository;

    @Override
    public List<ArticleResponse> mapToArticleResponseList() {

        return articleRepository.findAll().stream()
                .map(article -> {

                    ArticleResponse articleResponse = new ArticleResponse();

                    articleResponse.setId(article.getId());
                    articleResponse.setAuthor(article.getAuthor());
                    articleResponse.setTitle(article.getTitle());
                    articleResponse.setContent(article.getContent());
                    articleResponse.setPublishedAt(article.getPublishedAt());
                    articleResponse.setCategories(article.getCategories().stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet()));

                    return articleResponse;

                })
                .toList();
    }

    @Override
    public ArticleResponse mapToArticleResponse(Article article) {

        ArticleResponse articleResponse = new ArticleResponse();

        articleResponse.setId(article.getId());
        articleResponse.setAuthor(article.getAuthor());
        articleResponse.setTitle(article.getTitle());
        articleResponse.setContent(article.getContent());
        articleResponse.setPublishedAt(article.getPublishedAt());
        articleRepository.findById(article.getId())
                .ifPresent(a -> articleResponse.setCategories(a.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet())));

        return articleResponse;

    }

    @Override
    public void mapToUpdateArticle(UpdateArticle article, Article updateArticle) {

        if (article.getAuthor_id() != 0) updateArticle.setAuthor(authorRepository.findById(article.getAuthor_id())
                .orElseThrow(() -> new ArticleNotFoundException("Author not found with id: " + article.getAuthor_id())));
        else article.setAuthor_id(updateArticle.getAuthor().getId());

        if (article.getTitle() != null) updateArticle.setTitle(article.getTitle());
        else article.setTitle(updateArticle.getTitle());

        if (article.getContent() != null) updateArticle.setContent(article.getContent());
        else article.setContent(updateArticle.getContent());

        if (article.getPublishedAt() != null) updateArticle.setPublishedAt(article.getPublishedAt());
        else article.setPublishedAt(updateArticle.getPublishedAt());

        if (article.getCategories() != null) {

            updateArticle.setCategories(article.getCategories().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ArticleNotFoundException("Category not found with id: " + categoryId)))
                    .collect(Collectors.toSet()));

        } else article.setCategories(updateArticle.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Override
    public Article mapCreateAticleToArticle(CreateArticle createArticle) {
        Article article = new Article();
        if (articleRepository.existsArticleByTitle(article.getTitle()))
            throw new ArticleAlreadyExsitsException("Article already exists with title: " + "'" + article.getTitle() + "'");
        article.setAuthor(authorRepository.findById(createArticle.getAuthorId())
                .orElseThrow(() -> new ArticleNotFoundException("Author not found with id: " + createArticle.getAuthorId())));
        article.setTitle(createArticle.getTitle());
        article.setContent(createArticle.getContent());
        article.setPublishedAt(createArticle.getPublishedAt());
        article.setCategories(createArticle.getCategories().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id)))
                .collect(Collectors.toSet()));
        return article;
    }


}
