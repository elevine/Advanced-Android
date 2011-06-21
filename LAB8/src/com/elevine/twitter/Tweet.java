package com.elevine.twitter;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Tweet implements Serializable{

	private static final long serialVersionUID = 1L;
	private User user = null;
	private Geo geo = null;
	private String text = null;
	
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Geo implements Serializable{
		private static final long serialVersionUID = 1L;
		private Double[] coordinates = null;
		public Double[] getCoordinates() {
			return coordinates;
		}
		public void setCoordinates(Double[] coordinates) {
			this.coordinates = coordinates;
		}
		
		
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class User implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String name = null;
		private String profileImageUrl;
		private String location = null;
		
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

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
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

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}
	
	
}
