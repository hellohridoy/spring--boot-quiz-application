package com.example.quiz.to.mircro.Quiz.controller;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.repository.QuestionRepository;
import com.example.quiz.to.mircro.Quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @GetMapping("/all-question")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/all-question/{difficultyLevel}")
    public ResponseEntity<List<Question>> getAllQuestionByDifficultyLevel(@PathVariable String difficultyLevel) {
        if (difficultyLevel.equals(null)) {
            throw new IllegalArgumentException("Difficulty Level cannot be null");
        }
        ResponseEntity<List<Question>> questions = questionService.getAllQuestionByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(questions.getBody());
    }

    @GetMapping("/questions/by-topic/{topic}/{limit}")
    public ResponseEntity<List<Question>> getRandomQuestionsByTopic(@PathVariable String topic,
                                                                    @PathVariable int limit) {
        try {
            if (topic == null || topic.trim().isEmpty()) {
                throw new IllegalArgumentException("Topic cannot be null or blank");
            }
            if (limit <= 0) {
                throw new IllegalArgumentException("Limit must be greater than 0");
            }
            if (limit > 20) {
                throw new IllegalArgumentException("Limit cannot be greater than 20");
            }

            List<Question> questions = questionRepository.getRandomQuestionsByTopic(topic.trim().toLowerCase(), limit);
            return ResponseEntity.ok(questions);
        } catch (IllegalArgumentException e) {
            log.warn("Validation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            log.error("Error fetching random questions by topic: {}", topic, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        String result = String.valueOf(questionService.addQuestion(question));
        if (result.equals(null)) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        return ResponseEntity.ok(result);
    }
}
