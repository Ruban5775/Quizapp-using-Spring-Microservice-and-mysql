package com.question.micro.Dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.question.micro.model.Questions;

@Repository
public interface QuestionsDao extends JpaRepository<Questions, Integer>{
	
	List<Questions> findByCategory(String category);
	
	List<Questions> findBydifficultyLevel(String difficultyLevel);

	@Query(value = "SELECT q.id FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT = :numQ", nativeQuery = true)
	List<Integer> findRandomQuestionsBycategory(String category, int numQ);

}
