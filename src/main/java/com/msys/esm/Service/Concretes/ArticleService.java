package com.msys.esm.Service.Concretes;

import com.msys.esm.Core.Util.Exceptions.ArticleAlreadyExsitsException;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.Mapper.Abstracts.ArticleMapper;
import com.msys.esm.Service.Abstracts.IArticleService;
import com.msys.esm.Core.DTO.Request.Create.CreateArticle;
import com.msys.esm.Core.DTO.Request.Update.UpdateArticle;
import com.msys.esm.Core.DTO.Response.ArticleResponse;
import com.msys.esm.Core.Util.Exceptions.ArticleNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.ArticleRepository;
import com.msys.esm.Repository.AuthorRepository;
import com.msys.esm.Repository.CategoryRepository;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Author;
import com.msys.esm.Model.Category;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.text.StringEscapeUtils;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ArticleService implements IArticleService {

    ArticleRepository articleRepository;
    ModelService mapper;
    AuthorRepository authorRepository;
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<ArticleResponse>> getAll() {

        List<Article> articles = articleRepository.findAll();

        if (articles.isEmpty()) return ResponseEntity.noContent().build();

        List<ArticleResponse> responseArticles = articles
                .stream()
                .map(a -> {

                    ArticleResponse articleResponse = new ArticleResponse();
                    articleResponse.setId(a.getId());
                    articleResponse.setAuthor(a.getAuthor());
                    articleResponse.setTitle(a.getTitle());
                    articleResponse.setContent(a.getContent());
                    articleResponse.setPublishedAt(a.getPublishedAt());
                    articleResponse.setCategories(articleRepository.getCategoriesIdsById(a.getId()));

                    return articleResponse;

                }).toList();

        return ResponseEntity.ok(responseArticles);

    }

    @Override
    public ResponseEntity<ArticleResponse> getById(int id) {

        Article article = getArticleById(id);


        return ResponseEntity.ok(ArticleMapper.mapToArticleResponse(article, articleRepository.getCategoriesIdsById(id)));

    }

    private Article getArticleById(int id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));

        Hibernate.initialize(article.getCategories());

        return article;

    }
    private Author getAuthorById(int id) {

        return authorRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Author not found with id: " + id));

    }
    @Override
    public ResponseEntity<CreateArticle> add(CreateArticle article) {

        if (articleRepository.existsArticleByTitle(article.getTitle()))
            throw new ArticleAlreadyExsitsException("Article already exists with title: " + article.getTitle());

        Article mappedArticle = mapper.forResponse().map(article, Article.class);

        Set<Category> givenCategories = article.getCategories().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id)))
                .collect(Collectors.toSet());

        mappedArticle.setAuthor(getAuthorById(article.getAuthorId()));

        mappedArticle.setCategories(givenCategories);

        articleRepository.save(mappedArticle);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }

    @Override
    public ResponseEntity<UpdateArticle> update(UpdateArticle article, int id) {

        Article updateArticle = getArticleById(id);

        CheckIds.check(updateArticle.getId(), id);

        if (article.getAuthor_id() != 0) updateArticle.setAuthor(getAuthorById(article.getAuthor_id()));
        else article.setAuthor_id(updateArticle.getAuthor().getId());
        if (article.getTitle() != null) updateArticle.setTitle(article.getTitle());
        else article.setTitle(updateArticle.getTitle());
        if (article.getContent() != null) updateArticle.setContent(article.getContent());
        else article.setContent(updateArticle.getContent());
        if (article.getPublishedAt() != null) updateArticle.setPublishedAt(article.getPublishedAt());
        else article.setPublishedAt(updateArticle.getPublishedAt());
        if (article.getCategories() != null){

//            articleRepository.deleteArticle(id);

            updateArticle.setCategories(article.getCategories().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new ArticleNotFoundException("Category not found with id: " + categoryId)))
                    .collect(Collectors.toSet()));

        }else article.setCategories(updateArticle.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        updateArticle.getCategories().forEach(category -> {
            category.getArticles().add(updateArticle);
            categoryRepository.save(category);
        });

        articleRepository.save(updateArticle);
//
//        ArticleMapper.mapToUpdateArticle(article, updateArticle);

        return ResponseEntity.ok(article);

    }

    @Override
    public ResponseEntity<ArticleResponse> delete(int id) {

        Article article = getArticleById(id);

        Hibernate.initialize(article.getCategories());
        Hibernate.initialize(article.getAuthor().getBooks());

        articleRepository.deleteArticle(id);
        articleRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ArticleMapper.mapToArticleResponse(article, articleRepository.getCategoriesIdsById(id)));

    }

}