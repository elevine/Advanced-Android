package com.elevine.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.elevine.twitter.widget.TweetAdapter;

public class MainActivity extends ListActivity {
	private static final String TWITTER_URL = "http://twitter-elevine.apigee.com/1/statuses/public_timeline.json";
	private static final String LOCAL = "http://10.0.2.2:8000/public_timeline.json";
	private static final String TAG = "MainActivity";
	public static final String EXTRA_TWEETS = "extra_tweets";
	private RestTemplate restTemplate = new RestTemplate();
	
	private ProgressDialog pd = null;
	private List<Tweet> tweets = new ArrayList<Tweet>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();

		if (getIntent().hasExtra(EXTRA_TWEETS)) {
			Bundle extras = getIntent().getExtras();
			if (extras.get(EXTRA_TWEETS) != null) {
				this.tweets = (List<Tweet>) extras.get(EXTRA_TWEETS);
			}
		} else {
			new FetchTimelineTask().execute();
		}
	}

	private class FetchTimelineTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pd = ProgressDialog.show(MainActivity.this, "Getting Tweets",
					"Fetching timeline!", false);
		}

		@Override
		protected Void doInBackground(Void... params) {
			Tweet[] tweetArray = restTemplate.getForObject(LOCAL,
					Tweet[].class);
			tweets = Arrays.asList(tweetArray);
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			pd.dismiss();

			TweetAdapter ta = new TweetAdapter(MainActivity.this, tweets, R.layout.tweet_row);
			setListAdapter(ta);

		}

	}

	public List<Tweet> getTweets() {
		return tweets;
	}

}