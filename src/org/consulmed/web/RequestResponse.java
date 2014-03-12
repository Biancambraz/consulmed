package org.consulmed.web;

public class RequestResponse 
{
	private String errorMessage;
	private String response;
	
	public String getMessage() {
		return errorMessage;
	}
	
	public void setMessage(String message) {
		this.errorMessage = message;
	}
	
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
}
