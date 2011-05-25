package com.elevine.twitter.test;


import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.elevine.twitter.Tweet;

public class TweetTest {
	private ObjectMapper mapper = new ObjectMapper();
	private Tweet[] tweets = null;
	
	@Before
	public void setUp() throws Exception {

		tweets = mapper.readValue(new File("res/public_timeline.json"), Tweet[].class);
		assertNotNull(tweets);
	}
	
	@Test
	public void testTweetName(){
		assertEquals("RAY,J", tweets[0].getUser().getName());	
	
	}
	
	@Test
	public void testTweetText(){
		String expected = "KIM KARDASHIAN IS OFFICIALLY ENGAGED!!! (DETAILS OF THE ROMANTIC PROPOSAL AND RING PICS INSIDE) - http://bit.ly/kOD7RP";
		assertEquals(expected, tweets[0].getText());
	}

}
