package com.example.demo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Survey;

@Repository
public class SurveyDaoImpl implements SurveyDao {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SurveyDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertSurvey(Survey survey) {
		jdbcTemplate.update("INSERT INTO survey(age, satisfaction, comment, created) VALUES(?, ?, ?, ?)",
				survey.getAge(), survey.getSatisfaction(), survey.getComment(), survey.getCreated());
	}

	@Override
	public List<Survey> getAll() {
		String sql = "SELECT id, age, satisfaction, comment, created FROM survey";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
//		一発目にsurveyテーブルから抽出した内容がどんな形で抽出できているのかを確かめる。
		System.out.println("resultListは→　" + resultList);
		List<Survey> list = new ArrayList<Survey>();
//		テーブルの1行ずつEntityに詰めていく。
		for(Map<String, Object> result : resultList) {
			Survey survey = new Survey();
			survey.setId((int)result.get("id"));
			survey.setAge((int)result.get("age"));
			survey.setSatisfaction((int)result.get("satisfaction"));
			survey.setComment((String)result.get("comment"));
			survey.setCreated(((Timestamp)result.get("created")).toLocalDateTime());
			list.add(survey);
		}
		return list;
	}
	
}
