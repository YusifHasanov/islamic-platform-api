package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.SpringContext;
import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;
import com.msys.esm.Repository.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.stream.Collectors;

@Mapper(imports = {CategoryNotFoundException.class, Article.class, Category.class, CategoryRepository.class, Collectors.class, HashSet.class})
public interface CategoryMapper {

    CategoryRepository categoryRepository = SpringContext.getBean(CategoryRepository.class);
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "articles",  expression = "java(category.getArticles() != null ? category.getArticles().stream().map(Article::getId).collect(Collectors.toSet()) : new HashSet<>())")
    @Mapping(target = "subCategories", expression = "java(category.getChildren() != null ? category.getChildren().stream().map(Category::getId).collect(Collectors.toSet()) : new HashSet<>())")
    @Mapping(target = "parentId", expression = "java(category.getParent() != null ? category.getParent().getId() : 0)")

    void entityToResponse(@MappingTarget CategoryResponse categoryResponse, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "children", expression = "java(createCategory.getSubCategories().stream().map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(\"Category not found with id: \" + id))).collect(Collectors.toSet()))")
    @Mapping(target = "parent", expression = "java(categoryRepository.findById(createCategory.getParentId()).orElseThrow(() -> new CategoryNotFoundException(\"Category not found with id: \" + createCategory.getParentId())))")
    void createDtoToEntity(@MappingTarget Category category, CreateCategory createCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articles", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "name", expression = "java(updateCategory.getName() != null ? updateCategory.getName() : category.getName())")
    @Mapping(target = "children", expression = "java(updateCategory.getSubCategories() != null ?" +
            " updateCategory.getSubCategories().stream()" +
            ".map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(\"Category not found with id: \" + id)))" +
            ".collect(Collectors.toSet()) : " +
            "category.getChildren())")
    @Mapping(target = "parent", expression = "java(updateCategory.getParentId() != 0 ?" +
            "categoryRepository.findById(updateCategory.getParentId())" +
            ".orElseThrow(() -> new CategoryNotFoundException(\"Category not found with id: \" + updateCategory.getParentId())) :" +
            "category.getParent())")
    void updateDtoToEntity(@MappingTarget Category category, UpdateCategory updateCategory);

}
