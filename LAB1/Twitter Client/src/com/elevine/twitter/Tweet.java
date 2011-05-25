package com.elevine.twitter;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Tweet {

	private User user = null;
	private String text = null;
	
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class User{

		private String name = null;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		
		
	}
	
	public Tweet(){}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
		
}
