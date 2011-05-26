package com.elevine.twitter.test;

import java.util.ArrayList;

import com.elevine.twitter.MainActivity;
import com.elevine.twitter.Tweet;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	
	public void testIntentWithTweet(){
		Intent i = new Intent(getInstrumentation().getContext(), MainActivity.class);
		Tweet t = new Tweet();
		t.setText("foo");
		t.setUser(new Tweet.User());
		t.getUser().setName("bar");
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(t);
		i.putExtra(MainActivity.EXTRA_TWEETS, tweets);
	
		setActivityIntent(i);
		MainActivity activity = (MainActivity)getActivity();
		assertNotNull(activity.getTweets());
		assertEquals(1, activity.getTweets().size());
		assertEquals("foo", activity.getTweets().get(0).getText());
		
		
	}

}
