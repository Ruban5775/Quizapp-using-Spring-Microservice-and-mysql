package com.question.micro.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.sqm.tree.expression.AsWrapperSqmExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.question.micro.Dao.QuestionsDao;
import com.question.micro.model.Questions;
import com.question.micro.model.QuestionsWrapper;
import com.question.micro.model.Response;

@Service
public class QuestionService {
	
	@Autowired
	QuestionsDao questionsDao;

	public List<Questions> getAllQuestions() {
		
		return questionsDao.findAll();
	}

	public List<Questions> getAllQuestionsBycategory(String category) {
	
		return questionsDao.findByCategory(category);
	}

	public String addQuestion(Questions question) {
		
		questionsDao.save(question);
		
		return "Success";
		
	}

	public List<Questions> getAllQuestionsByDifficulty(String difficultyLevel) {
		
		return questionsDao.findBydifficultyLevel(difficultyLevel);
	}

	
	
	// For questions Microservice
	public ResponseEntity<List<Integer>> getQuestionsforQuiz(String categoryName, int numQuestions) {
	
		List<Integer> questions = questionsDao.findRandomQuestionsBycategory(categoryName, numQuestions);
		
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionsWrapper>> getQuestionsFromid(List<Integer> questionids) {
		
		List<QuestionsWrapper> wrappers = new ArrayList<>();
		List<Questions> questions= new ArrayList<>();
		
		
		for(Integer id: questionids) {
			questions.add(questionsDao.findById(id).get());
		}
		
		for(Questions question: questions) {
			QuestionsWrapper wrapper = new QuestionsWrapper();
			
			wrapper.setId(question.getId());
			wrapper.setQuestion(question.getQuestion());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			
		
			wrappers.add(wrapper);
		}
		
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		
		
      int right = 0;
		
		
		for(Response response: responses) {
			Questions questions = questionsDao.findById(response.getId()).get();
			if(response.getResponse().equals(questions.getCorrect_answer())) {
				
				right++;
			}
			
		
		}
		
		return new ResponseEntity<>(right, HttpStatus.OK);	}

}
