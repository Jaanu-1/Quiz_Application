package com.spring.quizapp.repository;

import com.spring.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * from Question q WHERE q.category = :category ORDER BY RAND() LIMIT :numQues", nativeQuery = true)
    List<Question> findByCategoryLimitQuestions(String category, int numQues);
}
