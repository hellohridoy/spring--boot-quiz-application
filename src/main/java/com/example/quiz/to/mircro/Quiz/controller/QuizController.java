package com.example.quiz.to.mircro.Quiz.controller;

import com.example.quiz.to.mircro.Quiz.dto.QuestionDto;
import com.example.quiz.to.mircro.Quiz.entity.Response;
import com.example.quiz.to.mircro.Quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/api/v1/quiz/create-quiz")
    public ResponseEntity<String> createQuiz(
        @RequestParam String difficultyLevel,
        @RequestParam int numberOfQuestion,
        @RequestParam String tittle

    ) {
        return quizService.createQuiz(difficultyLevel, numberOfQuestion, tittle);
    }

    @GetMapping("/api/v1/quiz/get/{id}/quiz-question")
    public ResponseEntity<List<QuestionDto>> getQuizQuestion(
        @PathVariable Integer id
    ) {
        return quizService.getQuizQuestions(id);
    }


    @PostMapping("/api/v1/quiz/question-submit/{id}")
    public ResponseEntity<Integer> submitQuizQuestion(
        @PathVariable Integer id,
        @RequestBody List<Response> responses) {
        return quizService.calculateResult(id,responses);

    }
}
