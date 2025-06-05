package com.example.quiz.to.mircro.Quiz.service;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getAllQuestionByCategory(String difficultyLevel) {
        return questionRepository.findByDifficultyLevel(difficultyLevel);
    }

    public String addQuestion(Question question) {
        questionRepository.save(question);
        return "Question added successfully";
    }
}
