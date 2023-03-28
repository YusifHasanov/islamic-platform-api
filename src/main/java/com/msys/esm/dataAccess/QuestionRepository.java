package com.msys.esm.dataAccess;

import com.msys.esm.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
}
