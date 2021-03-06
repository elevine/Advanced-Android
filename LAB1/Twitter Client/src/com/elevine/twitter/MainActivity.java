package com.elevine.twitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.elevine.twitter.widget.TweetAdapter;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends ListActivity {
	private static final String TWITTER_URL = "http://twitter-elevine.apigee.com/1/statuses/public_timeline.json";
	private static final String LOCAL = "http://10.0.2.2:8000/public_timeline.json";
	private static final String TAG = "MainActivity";
	public static final String EXTRA_TWEETS = "extra_tweets";

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
				setListAdapter(new TweetAdapter(MainActivity.this, tweets, R.layout.tweet_row));
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
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(LOCAL);
			HttpResponse response;
			try {
				response = client.execute(request);
				ObjectMapper mapper = new ObjectMapper();				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent()));

				StringBuilder responseBody = new StringBuilder();
				String nextLine = null;

				while ((nextLine = reader.readLine()) != null) {
					responseBody.append(nextLine);

				}
				Log.d(TAG, responseBody.toString());
				Tweet[] tweetArray = mapper.readValue(responseBody.toString(), Tweet[].class);

				tweets = Arrays.asList(tweetArray);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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