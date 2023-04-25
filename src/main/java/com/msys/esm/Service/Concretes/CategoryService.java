package com.msys.esm.Service.Concretes;

import com.msys.esm.Model.Article;
import com.msys.esm.Service.Abstracts.ICategoryService;
import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.Messages.MessageAndStatusCode;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.CategoryRepository;
import com.msys.esm.Model.Category;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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
public class CategoryService implements ICategoryService {

    CategoryRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<CategoryResponse>> getAll() {

        List<Category> categories = repository.findAll();

        List<CategoryResponse> categoryResponse = categories.stream()
                .map(category -> {
                    CategoryResponse categoryResponse2 = new CategoryResponse();

                    categoryResponse2.setId(category.getId());
                    categoryResponse2.setName(category.getName());
                    categoryResponse2.setArticles(category.getArticles().stream()
                            .map(Article::getId)
                            .collect(Collectors.toSet()));
                    categoryResponse2.setSubCategories(category.getChildren().stream()
                            .map(Category::getId)
                            .collect(Collectors.toSet()));
                    categoryResponse2.setParentId(category.getParent() != null ? category.getParent().getId() : 0);

                    return categoryResponse2;

                }).toList();

        return ResponseEntity.ok(categoryResponse);

    }

    @Override
    public ResponseEntity<CategoryResponse> getById(int id) {

        Category foundCategory = getFoundedCategory(id);
        CategoryResponse response = new CategoryResponse();

        response.setName(foundCategory.getName());
        response.setId(foundCategory.getId());
        response.setParentId(foundCategory.getParent() != null ? foundCategory.getParent().getId() : 0);
        response.setSubCategories(foundCategory.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
        response.setArticles(foundCategory.getArticles().stream()
                .map(Article::getId)
                .collect(Collectors.toSet()));

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<CreateCategory> add(CreateCategory category) {

        Category parentCategory = category.getParentId() == 0 ? null : repository.findById(category.getParentId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id:" + category.getParentId()));
        Category newCategory = new Category();
        Set<Category> subCategories = new HashSet<>();

        newCategory.setName(category.getName());
        newCategory.setParent(parentCategory);
        category.getSubCategories().forEach(subCategory -> {

            Category foundCategory = getFoundedCategory(subCategory);

            foundCategory.setParent(newCategory);
            subCategories.add(foundCategory);

        });

        newCategory.setChildren(subCategories);

        repository.save(newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @Override
    public ResponseEntity<UpdateCategory> update(UpdateCategory category, int id) {

        Category foundCategory = getFoundedCategory(id);

        CheckIds.check(foundCategory.getId(), id);

        Category parentCategory = category.getParentId() == 0 ? null : repository.findById(category.getParentId()).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id:" + category.getParentId()));

        foundCategory.setParent(parentCategory);

        if (category.getName() != null) foundCategory.setName(category.getName());
        else category.setName(foundCategory.getName());
        if (category.getParentId() != 0) foundCategory.setParent(parentCategory);
        else category.setParentId(foundCategory.getParent() != null ? foundCategory.getParent().getId() : 0);
        if (category.getSubCategories() != null ) {
            Set<Category> subCategories = new HashSet<>();
            category.getSubCategories().forEach(subCategory -> {

                Category foundCategory2 = repository.findById(subCategory).orElseThrow(
                        () -> new CategoryNotFoundException("Category not found with id:" + subCategory));

                foundCategory2.setParent(foundCategory);
                subCategories.add(foundCategory2);

            });

            foundCategory.setChildren(subCategories);

        }else category.setSubCategories(foundCategory.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        repository.save(foundCategory);

        return ResponseEntity.ok(category);

    }

    @Override
    public ResponseEntity<?> delete(int id) {
        Category foundCategory = getFoundedCategory(id);
        try {
            repository.delete(foundCategory);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new MessageAndStatusCode("Category not empty", 400), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    private Category getFoundedCategory(int id) {

        return repository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id:" + id));

    }

}
