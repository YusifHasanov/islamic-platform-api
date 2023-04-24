package com.msys.esm.business.concretes;

import com.msys.esm.business.abstracts.ICategoryService;
import com.msys.esm.core.dto.Request.Create.CreateCategory;
import com.msys.esm.core.dto.Request.Update.UpdateCategory;
import com.msys.esm.core.dto.Response.CategoryResponse;
import com.msys.esm.core.util.Exceptions.CategoryNotFoundException;
import com.msys.esm.core.util.Messages.MessageAndStatusCode;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.CategoryRepository;
import com.msys.esm.entities.Category;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
                    Set<Integer> articles = repository.getArticlesById(category.getId());
                    CategoryResponse categoryResponse2 = new CategoryResponse();
                    categoryResponse2.setId(category.getId());
                    categoryResponse2.setName(category.getName());
                    categoryResponse2.setArticles(articles);
                    categoryResponse2.setSubCategories(repository.findCategoriesByParentId(category.getId()));
                    categoryResponse2.setParentId(category.getParent() != null ? category.getParent().getId() : 0);

                    return categoryResponse2;
                }).toList();

        return ResponseEntity.ok(categoryResponse);
    }

    @Override
    public ResponseEntity<CategoryResponse> getById(int id) {
        Category foundCategory = repository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id:" + id));
        CategoryResponse response = mapper.forResponse()
                .map(foundCategory, CategoryResponse.class);
        response.setSubCategories(repository.findCategoriesByParentId(foundCategory.getId()));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateCategory> add(CreateCategory category) {

        Category parentCategory = category.getParentId() == 0 ? null : repository.findById(category.getParentId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id:" + category.getParentId()));
        Category newCategory = new Category();
        Set<Category> children = repository.findCategoriesByParentIdRetrunCategories(category.getParentId()) !=null ?
                repository.findCategoriesByParentIdRetrunCategories(category.getParentId()) : new HashSet<>();

        newCategory.setName(category.getName());
        newCategory.setParent(parentCategory);

        // TODO: check if subcategories are valid
        // TODO: check if subcategories are not parent of this category
        // TODO: if all is ok, add subcategories to this category

        if (category.getSubCategories() == null) {
            newCategory.setChildren(new HashSet<>());
        } else {
            Set<Category> subCategories = new HashSet<>();
            category.getSubCategories().forEach(subCategory -> {
                Category foundCategory = repository.findById(subCategory).orElseThrow(
                        () -> new CategoryNotFoundException("Category not found with id:" + subCategory));
                foundCategory.setParent(newCategory);
                subCategories.add(foundCategory);
                System.out.println(foundCategory);
                System.out.println(subCategories);
                repository.save(foundCategory);
            });
            newCategory.setChildren(subCategories);
        }

        repository.save(newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @Override
    public ResponseEntity<UpdateCategory> update(UpdateCategory category, int id) {

        Category foundCategory = repository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id:" + id));

        CheckIds.check(foundCategory.getId(), id);

        if (Integer.valueOf(category.getParentId()) != null) {
            Category parentCategory = category.getParentId() == 0 ? null : repository.findById(category.getParentId()).orElseThrow(
                    () -> new CategoryNotFoundException("Category not found with id:" + category.getParentId()));

            foundCategory.setParent(parentCategory);
        }
        if (category.getName() != null) {
            foundCategory.setName(category.getName());
        }
        if (category.getSubCategories() != null){
            foundCategory.setChildren(repository.findCategoriesByParentIdRetrunCategories(foundCategory.getId()));
        }

        repository.save(foundCategory);

        return ResponseEntity.ok(category);
    }

    @Override
    public ResponseEntity<MessageAndStatusCode> delete(int id) {
        repository.deleteById(id);
        return new ResponseEntity<>(new MessageAndStatusCode("Category deleted successfully", 200), HttpStatus.OK);
    }
}
