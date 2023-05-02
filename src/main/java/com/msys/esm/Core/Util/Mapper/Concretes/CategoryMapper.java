package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.Mapper.Abstracts.ICategoryMapper;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;
import com.msys.esm.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper implements ICategoryMapper {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createDtoToEntity(CreateCategory createCategory) {

        Category category = new Category();

        category.setName(createCategory.getName());
        category.setParent(createCategory.getParentId() != 0 ? categoryRepository.findById(createCategory.getParentId())
                .orElseThrow(() -> new CategoryNotFoundException("Parent Category not found with id: " + createCategory.getParentId())) : null);
        category.setChildren(createCategory.getSubCategories().stream()
                .map(subCategoryId -> {
                    Category foundedSubCategory = categoryRepository.findById(subCategoryId)
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + subCategoryId));
                    foundedSubCategory.setParent(category);
                    categoryRepository.save(foundedSubCategory);
                    return foundedSubCategory;
                })
                .collect(Collectors.toSet()));

        return category;

    }

    @Override
    public void updateDtoToEntity(Category category, UpdateCategory updateCategory) {

        if (updateCategory.getName() != null)
            category.setName(updateCategory.getName());
        else updateCategory.setName(category.getName());

        if (updateCategory.getSubCategories() != null) {

            Set<Category> actualSubCategories = category.getChildren();

            updateCategory.getSubCategories().forEach(subCategoryId -> {
                if (actualSubCategories.stream().anyMatch(category1 -> category1.getId() == subCategoryId))
                    throw new IllegalArgumentException("Category already has this sub category with id: " + subCategoryId);
                if(subCategoryId == category.getId())
                    throw new IllegalArgumentException("Category can not be sub category of itself with id: " + category.getId());
                Category foundedCategory = categoryRepository.findById(subCategoryId)
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + subCategoryId));

                foundedCategory.setParent(category);
                actualSubCategories.add(foundedCategory);

            });

            category.setChildren(actualSubCategories);

            /*category.getChildren() != null ? category.getChildren().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()) : new HashSet<>()*/

        } else updateCategory.setSubCategories(category.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        if (updateCategory.getParentId() != 0)
            if (updateCategory.getParentId() != category.getId())
                category.setParent(categoryRepository.findById(updateCategory.getParentId())
                        .orElseThrow(() -> new CategoryNotFoundException("Parent Category not found with id: " + updateCategory.getParentId())));
            else throw new IllegalArgumentException("Parent Category id can not be same with Category id");
        else category.setParent(null);


    }

    @Override
    public CategoryResponse entityToResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSubCategories(category.getChildren().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
        categoryResponse.setParentId(category.getParent() != null ? category.getParent().getId() : 0);
        categoryResponse.setArticles(category.getArticles().stream()
                .map(Article::getId)
                .collect(Collectors.toSet()));
        return categoryResponse;
    }
}
