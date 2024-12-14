package com.question.micro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.question.micro.model.Questions;
import com.question.micro.model.QuestionsWrapper;
import com.question.micro.model.Response;
import com.question.micro.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {

		@Autowired
		QuestionService questionService;
		
		@Autowired
		Environment environment;
		
	
	@GetMapping("allquestions")
	public List<Questions> getAllQuestions() {
		
		return questionService.getAllQuestions();
	}
	
	@GetMapping("category/{category}")
	public List<Questions> getQuestinsByCategory(@PathVariable String category){
		
		return questionService.getAllQuestionsBycategory(category);
	}
	
	@GetMapping("difficulty/{difficulty}")
	public List<Questions> getQuestionsByDifficulty(@PathVariable("difficulty") String difficultyLevel){
		
		return questionService.getAllQuestionsByDifficulty(difficultyLevel);
	}
	
	@PostMapping("addQuestion")
	public String addQuestion(@RequestBody Questions question) {
		
		return questionService.addQuestion(question);
	}
	
	
	//In Micrservices, there are the main things for question service
	//Generate - request come form quiz service for questions
	//Get Questions for Quiz
	//getScore
	
	
	//quiz -> questionService -> generate question for quiz
	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String categoryName, @RequestParam int numQuestions) {
		
		
		return questionService.getQuestionsforQuiz(categoryName, numQuestions);
		
	}
	
	
	
	//get questions using question id
	//quiz -> questinService -> get questions using id
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionsWrapper>> getQuestionsFromid(@RequestBody List<Integer> questionids) {
//		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsFromid(questionids);
	}
	
	
	//Score
	//quiz -> questionService -> calculate Score
	
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
		return questionService.getScore(responses);
	}
	
	
	
}
