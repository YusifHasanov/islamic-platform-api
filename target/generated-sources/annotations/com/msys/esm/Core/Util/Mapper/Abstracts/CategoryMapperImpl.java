package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;
import com.msys.esm.Repository.CategoryRepository;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-06T22:01:02+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public void entityToResponse(CategoryResponse categoryResponse, Category category) {
        if ( category == null ) {
            return;
        }

        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );

        categoryResponse.setArticles( category.getArticles() != null ? category.getArticles().stream().map(Article::getId).collect(Collectors.toSet()) : new HashSet<>() );
        categoryResponse.setSubCategories( category.getChildren() != null ? category.getChildren().stream().map(Category::getId).collect(Collectors.toSet()) : new HashSet<>() );
        categoryResponse.setParentId( category.getParent() != null ? category.getParent().getId() : 0 );
    }

    @Override
    public void createDtoToEntity(Category category, CreateCategory createCategory) {
        if ( createCategory == null ) {
            return;
        }

        category.setName( createCategory.getName() );

        category.setChildren( createCategory.getSubCategories().stream().map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id))).collect(Collectors.toSet()) );
        category.setParent( categoryRepository.findById(createCategory.getParentId()).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + createCategory.getParentId())) );
    }

    @Override
    public void updateDtoToEntity(Category category, UpdateCategory updateCategory) {
        if ( updateCategory == null ) {
            return;
        }

        category.setName( updateCategory.getName() != null ? updateCategory.getName() : category.getName() );
        category.setChildren( updateCategory.getSubCategories() != null ? updateCategory.getSubCategories().stream().map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id))).collect(Collectors.toSet()) : category.getChildren() );
        category.setParent( updateCategory.getParentId() != 0 ?categoryRepository.findById(updateCategory.getParentId()).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + updateCategory.getParentId())) :category.getParent() );
    }
}
