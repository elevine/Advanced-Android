package com.elevine.twitter;

import org.springframework.web.client.RestTemplate;

import com.elevine.twitter.provider.TweetColumns;
import com.elevine.twitter.provider.TweetProvider;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;


public class TwitterService extends IntentService {
	private RestTemplate restTemplate = new RestTemplate();
	private static final String TWITTER_URL = "http://twitter-elevine.apigee.com/1/statuses/public_timeline.json";

	public TwitterService() {
		super("TwitterService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("TwitterService", "***INTENT***");
		Tweet[] tweetArray = restTemplate.getForObject(TWITTER_URL,
				Tweet[].class);

		ContentValues cv = new ContentValues();
		for (Tweet tweet : tweetArray) {
			cv.put(TweetColumns.TEXT, tweet.getText());
			cv.put(TweetColumns.NAME, tweet.getUser().getName());
			cv.put(TweetColumns.PROFILE_IMAGE_URL, tweet.getUser()
					.getProfileImageUrl());

			if ((tweet.getGeo() != null)
					&& (tweet.getGeo().getCoordinates() != null)) {
				Double[] coords = tweet.getGeo().getCoordinates();
				cv.put(TweetColumns.LATITUDE, coords[0]);
				cv.put(TweetColumns.LONGITUDE, coords[1]);
			}

			getContentResolver().insert(TweetProvider.CONTENT_URI, cv);

			cv.clear();
		}

	}

}
