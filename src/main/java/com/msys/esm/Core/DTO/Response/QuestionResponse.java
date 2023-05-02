package com.msys.esm.Core.DTO.Response;

import com.msys.esm.Model.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionResponse {

    int id;
    String question;
    String answer;
    @ToStringExclude
    @EqualsAndHashCode.Exclude
    Set<Integer> categories;
}
