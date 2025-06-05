package com.example.quiz.to.mircro.Quiz.repository;

import com.example.quiz.to.mircro.Quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository  extends JpaRepository<Quiz,Integer> {
}
