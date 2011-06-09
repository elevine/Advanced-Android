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
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.elevine.twitter.database.sqlite.TweetOpenHelper;
import com.elevine.twitter.provider.TweetColumns;
import com.elevine.twitter.provider.TweetProvider;
import com.elevine.twitter.widget.TweetCursorAdapter;

public class MainActivity extends GDListActivity {
	private static final String TWITTER_URL = "http://twitter-elevine.apigee.com/1/statuses/public_timeline.json";
	private static final String LOCAL = "http://10.0.2.2:8000/public_timeline.json";
	private static final String TAG = "MainActivity";
	public static final String EXTRA_TWEETS = "extra_tweets";
	private RestTemplate restTemplate = new RestTemplate();
	private String[] tweetColumns = {TweetColumns._ID, TweetColumns.NAME, TweetColumns.TEXT, TweetColumns.PROFILE_IMAGE_URL};
	private Handler handler = new Handler();
	private TweetCursorAdapter tweetCursorAdapter = null;
	private LoaderActionBarItem loaderItem = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

			
		loaderItem = (LoaderActionBarItem)addActionBarItem(Type.Refresh, R.id.action_bar_refresh);
		getContentResolver().registerContentObserver(TweetProvider.CONTENT_URI, true, new TweetContentObserver(handler));
	}


	@Override
	protected void onResume() {
		super.onResume();
		
		this.tweetCursorAdapter = new TweetCursorAdapter(this, findAll(), true);
		setListAdapter(tweetCursorAdapter);
		new FetchTimelineTask().execute();

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

			ContentValues cv = new ContentValues();
			for (Tweet tweet : tweetArray){
				cv.put(TweetColumns.TEXT, tweet.getText());
				cv.put(TweetColumns.NAME, tweet.getUser().getName());
				cv.put(TweetColumns.PROFILE_IMAGE_URL, tweet.getUser().getProfileImageUrl());
				
				getContentResolver().insert(TweetProvider.CONTENT_URI, cv);

				cv.clear();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loaderItem.setLoading(false);
			//tweetCursorAdapter.getCursor().requery();
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
	
	
	private String orderBy = TweetColumns._ID + " DESC";
	public Cursor findAll(){
		Cursor result = getContentResolver().query(TweetProvider.CONTENT_URI, tweetColumns, null, null, orderBy);
	
		 startManagingCursor(result);
		 return result;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Clear").setIcon(android.R.drawable.ic_menu_delete).setAlphabeticShortcut('c');
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getAlphabeticShortcut() == 'c'){
			getContentResolver().delete(TweetProvider.CONTENT_URI, null, null);
			//tweetCursorAdapter.getCursor().requery();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	private class TweetContentObserver extends ContentObserver{

		public TweetContentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			tweetCursorAdapter.getCursor().requery();
		}
		
	}
	
	
}