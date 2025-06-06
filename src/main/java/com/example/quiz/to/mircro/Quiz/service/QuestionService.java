package com.example.quiz.to.mircro.Quiz.service;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionRepository.findAll();
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching all questions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<Question>> getAllQuestionByDifficultyLevel(String difficultyLevel) {
        try {
            List<Question> questions = questionRepository.findByDifficultyLevel(difficultyLevel);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching questions by difficulty level: {}", difficultyLevel, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return ResponseEntity.status(HttpStatus.CREATED).body("Question added successfully");
        } catch (Exception e) {
            log.error("Error adding question", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add question");
        }
    }

    public ResponseEntity<List<Question>> getRandomQuestionsByTopic(String topic, int limit) {
        try {
            List<Question> questions = questionRepository.getRandomQuestionsByTopic(topic, limit);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            log.error("Error fetching random questions by topic: {}", topic, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


}
