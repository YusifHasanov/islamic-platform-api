package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.DTO.Response.QuestionResponse;
import com.msys.esm.Model.Question;

public interface IQuestionMapper {

    Question mapUpdateToQuestion(Question question, UpdateQuestion updateQuestion);

    Question mapCreateToQuestion(CreateQuestion createQuestion);
    QuestionResponse mapToResponse(Question question);
}
