package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Introthers;

public interface IntrothersService {
	
//	  新規ユーザ登録
	void save(Introthers introthers);
	
//	  全件取得
	List<Introthers> getAll();
	
//	  1ユーザ取得
	Introthers getOneUser(int userId);
	
//	  1ユーザ更新
	void updateOneUser(Introthers introthers);
	
//	  1ユーザ取得（Introthers型）
	Optional<Introthers> getUser(int userId);

}
