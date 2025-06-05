package com.example.quiz.to.mircro.Quiz.repository;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByDifficultyLevel(String difficultyLevel);


    @Query(value = "SELECT * FROM question WHERE difficulty_level = :difficultyLevel ORDER BY random() LIMIT :limit", nativeQuery = true)
    List<Question> getRandomQuestions(@Param("difficultyLevel") String difficultyLevel, @Param("limit") int limit);

}
