package com.spring.quizapp.service;

import com.spring.quizapp.model.Question;
import com.spring.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

   /* public List<Question> getQuestionsFromCategory(String category) {
        List<Question> allQuestions = getAllQuestions();
        return allQuestions.stream().filter(question -> question.getCategory().equalsIgnoreCase(category)).toList();
    }*/

    public ResponseEntity<List<Question>> getQuestionsFromCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
    }
}
