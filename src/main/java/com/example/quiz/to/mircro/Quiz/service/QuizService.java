package com.example.quiz.to.mircro.Quiz.service;

import com.example.quiz.to.mircro.Quiz.dto.QuestionDto;
import com.example.quiz.to.mircro.Quiz.entity.Question;
import com.example.quiz.to.mircro.Quiz.entity.Quiz;
import com.example.quiz.to.mircro.Quiz.entity.Response;
import com.example.quiz.to.mircro.Quiz.repository.QuestionRepository;
import com.example.quiz.to.mircro.Quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;


    public ResponseEntity<String>createQuiz(String difficultyLevel,int numberOfQuestions,String tittle) {

        List<Question> questions = questionRepository.getRandomQuestions(difficultyLevel,numberOfQuestions);

        Quiz quiz = new Quiz();
        quiz.setTitle(tittle);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDto>> getQuizQuestions(Integer id) {
         Optional<Quiz> quiz = quizRepository.findById(id);
         List<Question> questionsFromDB = quiz.get().getQuestions();
         List<QuestionDto> questionForUser = new ArrayList<>();
         for (Question question : questionsFromDB) {
             QuestionDto questionDto = new QuestionDto(question.getId(),
                 question.getQuestionTittle(), question.getOption1(),
                 question.getOption2(), question.getOption3(), question.getOption4());
             questionForUser.add(questionDto);
         }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepository.findById(id).get();
        List<Question>questions = quiz.getQuestions();
        int correct = 0;
        int index = 0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(index).getRightAnswer()))
                correct ++;

            index++;
        }
        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
