package com.example.demo.app.introthers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserForm {
		
	@NotNull
	@Size(min = 1, max = 20, message = "1文字以上20文字以下で入力してください。")
	private String name;
	
	private String comment;
	
	public CreateUserForm() { }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
