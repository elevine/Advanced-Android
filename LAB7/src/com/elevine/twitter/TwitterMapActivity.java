package com.elevine.twitter;

import greendroid.app.GDMapActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.elevine.twitter.map.TweetOverlay;
import com.elevine.twitter.provider.TweetColumns;
import com.elevine.twitter.provider.TweetProvider;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class TwitterMapActivity extends GDMapActivity{
	
	private String[] tweetColumns = { TweetColumns._ID, TweetColumns.NAME,
			TweetColumns.TEXT, TweetColumns.PROFILE_IMAGE_URL, TweetColumns.LATITUDE , TweetColumns.LONGITUDE, TweetColumns.LOCATION };
	private MapView map = null;
	private LoaderActionBarItem loaderItem = null;
	private String geoTweetSelection = "latitude != '' AND longitude != ''";
	private TweetOverlay tweetOverlay = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.map);
		
		loaderItem = (LoaderActionBarItem) addActionBarItem(Type.Refresh,
				R.id.action_bar_refresh);
		
		map = (MapView)this.findViewById(R.id.tweet_map);
        map.setBuiltInZoomControls(true);
        tweetOverlay = new TweetOverlay(getResources().getDrawable(R.drawable.text), map);
        map.getOverlays().add(tweetOverlay);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new MarkersTask().execute();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class MarkersTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			Cursor results = getContentResolver().query(TweetProvider.CONTENT_URI, tweetColumns, geoTweetSelection, null, null);
			startManagingCursor(results);
			
			if((results != null) && (results.moveToFirst())){
				int latColumn = results.getColumnIndex(TweetColumns.LATITUDE);
				int lonColumn = results.getColumnIndex(TweetColumns.LONGITUDE);
				int tweetColumn = results.getColumnIndex(TweetColumns.TEXT);
				int nameColumn = results.getColumnIndex(TweetColumns.NAME);
				do{
					
					double lat = results.getDouble(latColumn);
					double lon = results.getDouble(lonColumn);
					String tweet = results.getString(tweetColumn);
					String name = results.getString(nameColumn);
					
					GeoPoint point = new GeoPoint((int)(lat*1E6),(int)(lon*1E6));
					OverlayItem overlayItem = new OverlayItem(point, name, tweet);
					tweetOverlay.addItem(overlayItem);
				}while(results.moveToNext());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			loaderItem.setLoading(false);
		}
	}
	
	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {

		switch (item.getItemId()) {
		case R.id.action_bar_refresh:
			new MarkersTask().execute();
			break;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}
		return true;
	}
	
	
	
}
