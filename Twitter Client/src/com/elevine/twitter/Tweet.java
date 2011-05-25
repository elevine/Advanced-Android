package com.elevine.twitter;

public class Tweet {
	String message = null;
	String username = null;
	String image = null;
	
	public Tweet(){}
	
	
	public Tweet(String message, String username, String image) {
		super();
		this.message = message;
		this.username = username;
		this.image = image;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
