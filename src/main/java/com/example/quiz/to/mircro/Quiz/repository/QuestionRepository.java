package com.example.quiz.to.mircro.Quiz.repository;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByDifficultyLevel(String difficultyLevel);
}
