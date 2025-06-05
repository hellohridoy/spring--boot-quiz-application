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

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository; // Dependency for quiz-related DB operations
    private final QuestionRepository questionRepository; // Dependency for question-related DB operations

    // Method to create a quiz with given difficulty, number of questions, and title
    public ResponseEntity<String> createQuiz(String difficultyLevel, int numberOfQuestions, String title) {
        // Validate difficulty level
        if (difficultyLevel == null || difficultyLevel.isBlank()) {
            return new ResponseEntity<>("Difficulty level is required.", HttpStatus.BAD_REQUEST);
        }

        // Validate question count
        if (numberOfQuestions <= 0) {
            return new ResponseEntity<>("Number of questions must be greater than zero.", HttpStatus.BAD_REQUEST);
        }

        // Validate quiz title
        if (title == null || title.isBlank()) {
            return new ResponseEntity<>("Title is required.", HttpStatus.BAD_REQUEST);
        }

        // Get random questions from repository based on difficulty and count
        List<Question> questions = questionRepository.getRandomQuestions(difficultyLevel, numberOfQuestions);


        // Create a new quiz object
        Quiz quiz = new Quiz();
        quiz.setTitle(title); // Set quiz title
        quiz.setQuestions(questions); // Assign questions to quiz
        quizRepository.save(quiz); // Save quiz to database

        // Return success response
        return new ResponseEntity<>("Quiz created successfully.", HttpStatus.CREATED);
    }

    // Method to fetch quiz questions for a given quiz ID
    public ResponseEntity<List<QuestionDto>> getQuizQuestions(Integer id) {
        // Validate ID
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Bad request if ID is null
        }

        // Fetch quiz from DB
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return not found if quiz doesn't exist
        }

        // Get questions from the quiz
        List<Question> questionsFromDB = quizOptional.get().getQuestions();
        List<QuestionDto> questionForUser = new ArrayList<>(); // List to store DTOs without correct answers

        // Convert each question to DTO (hide correct answer)
        for (Question question : questionsFromDB) {
            QuestionDto questionDto = new QuestionDto(
                question.getId(),
                question.getQuestionTittle(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4()
            );
            questionForUser.add(questionDto); // Add DTO to list
        }

        // Return list of questions to user
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    // Method to calculate quiz score based on user responses
    public ResponseEntity<Map<String, Object>> calculateResult(Integer id, List<Response> responses) {
        // Validate inputs
        if (id == null || responses == null || responses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Fetch quiz from DB
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = quizOptional.get();
        List<Question> questions = quiz.getQuestions();

        // Ensure number of responses matches number of questions
        if (responses.size() != questions.size()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int correct = 0;
        for (int i = 0; i < responses.size(); i++) {
            String givenAnswer = responses.get(i).getResponse();
            String correctAnswer = questions.get(i).getRightAnswer();
            if (givenAnswer != null && givenAnswer.equals(correctAnswer)) {
                correct++;
            }
        }

        // Calculate percentage
        int totalQuestions = questions.size();
        double percentage = (correct * 100.0) / totalQuestions;

        String message;
        if (percentage < 30.0) {
            message = "You failed.";
        } else if (percentage < 50.0) {
            message = "Fair attempt.";
        } else if (percentage < 80.0) {
            message = "Good job.";
        } else {
            message = "Great!";
        }

        // Build result map
        Map<String, Object> result = new HashMap<>();
        result.put("score", correct);
        result.put("percentage", percentage);
        result.put("message", message);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
