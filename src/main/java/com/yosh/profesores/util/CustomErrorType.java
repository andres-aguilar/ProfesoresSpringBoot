package com.yosh.profesores.util;

public class CustomErrorType {
	private String errorMessage;

	public CustomErrorType(String error) {
		super();
		this.errorMessage = error;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
