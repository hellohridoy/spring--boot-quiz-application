package com.example.quiz.to.mircro.Quiz.controller;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/question")
public class QuestionRestController {

    private final QuestionService questionService;

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
