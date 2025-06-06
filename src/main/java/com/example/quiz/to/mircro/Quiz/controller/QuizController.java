package com.example.quiz.to.mircro.Quiz.controller;

import com.example.quiz.to.mircro.Quiz.dto.QuestionDto;
import com.example.quiz.to.mircro.Quiz.entity.Response;
import com.example.quiz.to.mircro.Quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

        if(numberOfQuestion>50){
            return ResponseEntity.badRequest().body("Number of question is cant,t greater than 50");
        }
        if (tittle==null){
            return ResponseEntity.badRequest().body("Title is null");
        }
        String level = difficultyLevel.toLowerCase();

        if (level.equals("easy") || level.equals("medium") || level.equals("hard")) {
            return quizService.createQuiz(level, numberOfQuestion, tittle);
        } else {
            return ResponseEntity.badRequest().body("Invalid difficulty level. Allowed: easy, medium, hard");
        }

    }

    @GetMapping("/api/v1/quiz/get/{id}/quiz-question")
    public ResponseEntity<List<QuestionDto>> getQuizQuestion(
        @PathVariable Integer id
    ) {
        if(id==null){
            return ResponseEntity.badRequest().body(null);
        }
        return quizService.getQuizQuestions(id);
    }


    @PostMapping("/api/v1/quiz/question-submit/{id}")
    public ResponseEntity<Map<String, Object>> submitQuizQuestion(
        @PathVariable Integer id,
        @RequestBody List<Response> responses) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        if(responses==null){
            return ResponseEntity.badRequest().body(null);
        }

        return quizService.calculateResult(id,responses);

    }
}
