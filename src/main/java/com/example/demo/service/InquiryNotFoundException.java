package com.example.demo.service;

//  独自例外の設定
public class InquiryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InquiryNotFoundException(String message) {
		super(message);
	}

}
