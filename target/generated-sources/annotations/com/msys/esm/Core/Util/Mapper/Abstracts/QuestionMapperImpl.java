package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Model.Question;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-12T20:30:55+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public void updateQuestionFromDto(Question question, UpdateQuestion questionDto) {
        if ( questionDto == null ) {
            return;
        }

        if ( questionDto.getQuestion() != null ) {
            question.setQuestion( questionDto.getQuestion() );
        }
        else {
            question.setQuestion( "question.getQuestion()" );
        }

        question.setAnswer( questionDto.getAnswer() != null ? questionDto.getAnswer() : question.getAnswer() );

        updateQuestionCategories( question, questionDto );
    }
}
