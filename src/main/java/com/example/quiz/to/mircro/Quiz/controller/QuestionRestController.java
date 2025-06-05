package com.example.quiz.to.mircro.Quiz.controller;

import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QuestionRestController {

    public final QuestionService questionService;

    @GetMapping("/api/v1/question/all-question")
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/api/v1/question/all-question/{difficultyLevel}")
    public List<Question> getAllQuestionByDifficultyLevel(@PathVariable String difficultyLevel) {
        return questionService.getAllQuestionByCategory(difficultyLevel);
    }

    @PostMapping("/api/v1/question/add-question")
    public String addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }


}
