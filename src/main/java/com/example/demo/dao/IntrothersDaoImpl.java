package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Introthers;

@Repository
public class IntrothersDaoImpl implements IntrothersDao {

//	　jdbcTemplate変数をおく。
	private final JdbcTemplate jdbcTemplate;
	
//	　コンストラクターを作成。
	public IntrothersDaoImpl(JdbcTemplate jdbcTemplate) {
	  this.jdbcTemplate = jdbcTemplate;
    }	
	
//	　ユーザ新規登録
	@Override
	public void updateUser(Introthers introthers) {
//		テーブルをupdateする。
		jdbcTemplate.update("INSERT INTO introthers(name, comment) VALUES(?, ?)",
				introthers.getName(), introthers.getComment());
	}

//	　全件取得（index表示用）
	@Override
	public List<Introthers> getAll() {
//		SQL文を変数(sql)におく。
		String sql = "SELECT id, name, comment FROM introthers";
//		SQL文を実行し、List<Map<String, Object>>で受け取る。
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
//		Entityクラスの型に合わせたListをnewする。後々詰めていく。
		List<Introthers> list = new ArrayList<Introthers>();
//		受け取ったresultListを1行のIntrothersクラスのEntityに詰めていく。
		for(Map<String, Object> result : resultList) {
			Introthers introthers = new Introthers();
			introthers.setId((int)result.get("id"));
			introthers.setName((String)result.get("name"));
			introthers.setComment((String)result.get("comment"));
			list.add(introthers);
		}		
		return list;
	}
	
//	public List<Introthers> getOneUser(int userId) {
//		SQL文を変数におく。
//		String sql = "SELECT id, name, comment FROM introthers WHERE id = " + userId;
////		SQL文を実行してMap型データとして受け取る。
//		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
////		Entityを詰めるためにList型インスタンスをnewする。
//		List<Introthers> list = new ArrayList<Introthers>();
////		受け取ったMap型データを1行のEntity(Introthers)クラスに詰めていく。
//		for(Map<String, Object> result: resultList) {
//			Introthers introthers = new Introthers();
//			introthers.setId((int)result.get("id"));
//			introthers.setName((String)result.get("name"));
//			introthers.setComment((String)result.get("comment"));
//			list.add(introthers);
//		}		
//		return list;
//	}
	
//	　1つのユーザ情報取得
	@Override
	public Introthers getOneUser(int userId) {
//		SQL文を変数におく。
		String sql = "SELECT id, name, comment FROM introthers WHERE id = " + userId;
//		SQL文を実行してMap型データとして受け取る。
		Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql);
//		Introthers型インスタンスを作成
		Introthers user = new Introthers();
		user.setName((String)resultMap.get("name"));
		user.setComment((String)resultMap.get("comment"));
		return user;
	}
	
//	　1つのユーザ更新
	@Override
	public void updateOneUser(Introthers introthers) {
		jdbcTemplate.update("UPDATE introthers SET name = ?, comment = ? WHERE id = ?",
				introthers.getName(), introthers.getComment(), introthers.getId());
		
        //		ここから下の5行は自分で書いたもの
		//		テーブルをupdateする。
//		jdbcTemplate.update("UPDATE introthers"
//				+ " name = ?,"
//				+ " comment = ?"
//				+ " WHERE id = ?",
//				introthers.getName(), introthers.getComment(), introthers.getId());
	}
	
	@Override
	public Optional<Introthers> getUser(int userId) {
//		SQL文を変数に入れる。INNER JOIN は入れるべきなのか？
		String sql = "SELECT id, name, comment FROM introthers WHERE id = ?";
//		SQL文を実行してMap<String, Object>型のIntrothersを取得。
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, userId);
//		Map<String, Object>型のデータを新たに作成したIntrothers型に詰めこんでいく。
		Introthers user = new Introthers();
		user.setId((int)result.get("id"));
		user.setName((String)result.get("name"));
		user.setComment((String)result.get("comment"));
//		Introthers型のuserをOptionalでラップする。
		Optional<Introthers> userOpt = Optional.ofNullable(user);
		
		return userOpt;
	}

}
