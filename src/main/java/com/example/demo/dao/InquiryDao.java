package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Inquiry;

public interface InquiryDao {
	
//	データを格納
	void insertInquiry(Inquiry inquiry);
	
//	値を取得
	List<Inquiry> getAll();

}
