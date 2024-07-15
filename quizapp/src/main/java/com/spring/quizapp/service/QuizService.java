package com.spring.quizapp.service;

import com.spring.quizapp.model.Question;
import com.spring.quizapp.model.QuestionWrapper;
import com.spring.quizapp.model.Quiz;
import com.spring.quizapp.model.Response;
import com.spring.quizapp.repository.QuestionRepository;
import com.spring.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizRepository quizRepository;

    /**
     * Creates the quiz in given category with given number of questions and title and saves it in database
     * @param category in which quiz to be created
     * @param numQues number of questions in the quiz
     * @param title of the quiz
     * @return success response
     */
    public ResponseEntity<String> createQuiz(String category, int numQues, String title) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        List<Question> questions = questionRepository.findByCategoryLimitQuestions(category, numQues);
        quiz.setQuestionList(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /**
     * Fetch the quiz with given Id
     * @param id of the quiz to be fetched
     * @return list of questions in the quiz with given id
     */
    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> quizQuestions = quiz.isPresent() ? quiz.get().getQuestionList() : new ArrayList<>();
        List<QuestionWrapper> quizToUser= new ArrayList<>();
        for(Question q : quizQuestions) {
            QuestionWrapper wrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            quizToUser.add(wrapper);
        }
        return new ResponseEntity<>(quizToUser, HttpStatus.OK);
    }

    /**
     * Calculates the score for the answers submitted
     * @param id of the quiz
     * @param response of the quiz
     * @return score of the quiz
     */
    public ResponseEntity<Integer> calculateQuizScore(Integer id, List<Response> response) {
        Quiz quiz = quizRepository.findById(id).isPresent() ? quizRepository.findById(id).get() : null;
        int rightAnswer = 0;
        if(quiz != null) {
            List<Question> questions = quiz.getQuestionList();
            int i = 0;
            for (Response res : response) {
                if (res.getResponse().equals(questions.get(i).getRightAnswer())) {
                    rightAnswer++;
                }
                i++;
            }
        }
        return new ResponseEntity<>(rightAnswer, HttpStatus.OK);
    }
}
