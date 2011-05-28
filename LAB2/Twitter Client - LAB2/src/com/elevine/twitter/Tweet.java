package com.elevine.twitter;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Tweet implements Serializable{

	private static final long serialVersionUID = 1L;
	private User user = null;
	private String text = null;
	
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class User implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String name = null;
		private String profileImageUrl;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@JsonProperty(value="profile_image_url")
		public String getProfileImageUrl() {
			return profileImageUrl;
		}
		
		@JsonProperty(value="profile_image_url")
		public void setProfileImageUrl(String profileImageUrl) {
			this.profileImageUrl = profileImageUrl;
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
