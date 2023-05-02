package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.SpringContext;
import com.msys.esm.Model.Category;
import com.msys.esm.Model.Question;
import com.msys.esm.Repository.CategoryRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    CategoryRepository categoryRepository = SpringContext.getBean(CategoryRepository.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", source = "question", defaultValue = "question.getQuestion()")
    @Mapping(target = "answer", expression = "java(questionDto.getAnswer() != null ? questionDto.getAnswer() : question.getAnswer())")
    @Mapping(target = "categories", ignore = true)
    void updateQuestionFromDto(@MappingTarget Question question, UpdateQuestion questionDto);

    @AfterMapping
    default void updateQuestionCategories(@MappingTarget Question question, UpdateQuestion questionDto) {
        if (questionDto.getCategories() != null)
            question.setCategories(questionDto.getCategories().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id)))
                    .collect(Collectors.toSet()));
        else questionDto.setCategories(question.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

    }


}
