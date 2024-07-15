package com.spring.quizapp.controller;

import com.spring.quizapp.model.QuestionWrapper;
import com.spring.quizapp.model.Response;
import com.spring.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQues, @RequestParam String title) {
        return quizService.createQuiz(category, numQues, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("submit/{id}")
    public ResponseEntity<Integer> calculateQuizScore(@PathVariable Integer id, @RequestBody List<Response> response) {
        return quizService.calculateQuizScore(id, response);
    }
}
