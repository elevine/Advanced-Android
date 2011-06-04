package com.elevine.twitter;

import greendroid.app.GDListActivity;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.ActionBarItem;
import greendroid.widget.LoaderActionBarItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.elevine.twitter.database.sqlite.TweetOpenHelper;
import com.elevine.twitter.provider.TweetColumns;
import com.elevine.twitter.widget.TweetCursorAdapter;

public class MainActivity extends GDListActivity {
	private static final String TWITTER_URL = "https://api.twitter.com/1/statuses/public_timeline.json";
	private static final String LOCAL = "http://10.0.2.2:8000/public_timeline.json";
	private static final String TAG = "MainActivity";
	public static final String EXTRA_TWEETS = "extra_tweets";
	private RestTemplate restTemplate = new RestTemplate();
	private String[] tweetColumns = {TweetColumns._ID, TweetColumns.NAME, TweetColumns.TEXT, TweetColumns.PROFILE_IMAGE_URL};
	private List<Tweet> tweets = new ArrayList<Tweet>();
	private TweetOpenHelper dbHelper = null;
	private TweetCursorAdapter tweetCursorAdapter = null;
	private LoaderActionBarItem loaderItem = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new TweetOpenHelper(this);
		
		
		loaderItem = (LoaderActionBarItem)addActionBarItem(Type.Refresh, R.id.action_bar_refresh);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();
		
		this.tweetCursorAdapter = new TweetCursorAdapter(this, findAll(), true);
		setListAdapter(tweetCursorAdapter);
		
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
			loaderItem.setLoading(true);
		}

		@Override
		protected Void doInBackground(Void... params) {
			Tweet[] tweetArray = restTemplate.getForObject(TWITTER_URL,
					Tweet[].class);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			for (Tweet tweet : tweetArray){
				cv.put(TweetColumns.TEXT, tweet.getText());
				cv.put(TweetColumns.NAME, tweet.getUser().getName());
				cv.put(TweetColumns.PROFILE_IMAGE_URL, tweet.getUser().getProfileImageUrl());
				db.insert(TweetOpenHelper.TWEETS_TABLE_NAME, null, cv);
				cv.clear();
			}
			
			tweets = Arrays.asList(tweetArray);
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loaderItem.setLoading(false);
			tweetCursorAdapter.getCursor().requery();
		}

	}
	
	 @Override
	    public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {

	        switch (item.getItemId()) {
	            case R.id.action_bar_refresh :
	            	new FetchTimelineTask().execute();
	            break;
	            default:
	                return super.onHandleActionBarItemClick(item, position);
	        }
	        return true;
	 }
	
	public List<Tweet> getTweets() {
		return tweets;
	}
	
	private String orderBy = TweetColumns._ID + " DESC";
	public Cursor findAll(){
		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
		 Cursor result = db.query(TweetOpenHelper.TWEETS_TABLE_NAME, tweetColumns, null, null, null, null, orderBy);
		 startManagingCursor(result);
		 return result;
	}
	
}