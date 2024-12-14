package com.quiz.micro.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.quiz.micro.Dao.QuizDao;
import com.quiz.micro.feign.QuizInterface;
import com.quiz.micro.model.QuestionsWrapper;
import com.quiz.micro.model.Quiz;
import com.quiz.micro.model.Response;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuizInterface quizInterface;

	
	
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
	List<Integer> questions = quizInterface.getQuestionsforQuiz(category, numQ).getBody();
		
		Quiz quiz = new Quiz();
		

		quiz.setTitle(title);

		quiz.setQuestionIds(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);

		
	}

	public ResponseEntity<List<QuestionsWrapper>> getQuizQuestions(int id) {
	
	   Quiz quiz = quizDao.findById(id).get();
	   List<Integer> questionsIds = quiz.getQuestionIds();
	   ResponseEntity<List<QuestionsWrapper>> questions = quizInterface.getQuestionsFromid(questionsIds);

		
		return questions;
	}

	public ResponseEntity<Integer> calculateScore(int id, List<Response> responses) {

		ResponseEntity<Integer> score = quizInterface.getScore(responses);
		return score;
	}
}
