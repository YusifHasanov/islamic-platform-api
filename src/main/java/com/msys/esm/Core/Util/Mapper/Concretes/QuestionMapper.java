package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.DTO.Response.QuestionResponse;
import com.msys.esm.Core.Util.Exceptions.CategoryNotFoundException;
import com.msys.esm.Core.Util.Mapper.Abstracts.IQuestionMapper;
import com.msys.esm.Model.Category;
import com.msys.esm.Model.Question;
import com.msys.esm.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class QuestionMapper implements IQuestionMapper {
    CategoryRepository categoryRepository;

    @Override
    public Question mapUpdateToQuestion(Question question, UpdateQuestion updateQuestion) {

        if (updateQuestion.getQuestion() != null)
            question.setQuestion(updateQuestion.getQuestion());
        else updateQuestion.setQuestion(question.getQuestion());

        if (updateQuestion.getAnswer() != null)
            question.setAnswer(updateQuestion.getAnswer());
        else updateQuestion.setAnswer(question.getAnswer());

        if (updateQuestion.getCategories() != null)
            question.setCategories(updateQuestion.getCategories().stream()
                    .map(id -> categoryRepository.findById(id)
                            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id)))
                    .collect(Collectors.toSet()));
        else updateQuestion.setCategories(question.getCategories() != null ? question.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()): new HashSet<>());

        return question;
    }

    @Override
    public Question mapCreateToQuestion(CreateQuestion createQuestion) {

        Question question = new Question();

        question.setQuestion(createQuestion.getQuestion());
        question.setAnswer(createQuestion.getAnswer());
        question.setCategories(createQuestion.getCategories().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id)))
                .collect(Collectors.toSet()));

        return question;
    }

    @Override
    public QuestionResponse mapToResponse(Question question) {

        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setId(question.getId());
        questionResponse.setQuestion(question.getQuestion());
        questionResponse.setAnswer(question.getAnswer());
        questionResponse.setCategories(question.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));

        return questionResponse;
    }
}
