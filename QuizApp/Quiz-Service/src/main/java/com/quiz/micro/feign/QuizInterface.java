package com.quiz.micro.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.micro.model.QuestionsWrapper;
import com.quiz.micro.model.Response;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

	//Genegate questions form questionsService
	@GetMapping("question/generate")
	public ResponseEntity<List<Integer>> getQuestionsforQuiz(@RequestParam String categoryName, @RequestParam int numQuestions);
	
	
	
	//get questions using questionService
	//quiz -> questinService -> get questions using id
	@PostMapping("question/getQuestions")
	public ResponseEntity<List<QuestionsWrapper>> getQuestionsFromid(@RequestBody List<Integer> questionids);
	
	
	//get score form questionService
	//quiz -> questionService -> calculate Score
	
	@PostMapping("question/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
