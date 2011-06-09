package com.elevine.twitter.widget;

import greendroid.widget.AsyncImageView;

import com.elevine.twitter.R;
import com.elevine.twitter.provider.TweetColumns;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TweetCursorAdapter extends CursorAdapter{
	private LayoutInflater mInflater = null;
	int indexImageUrl;
	int indexText;
	int indexName;
	
	
	public TweetCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
		indexImageUrl = c.getColumnIndex(TweetColumns.PROFILE_IMAGE_URL);
		indexText = c.getColumnIndex(TweetColumns.TEXT);
		indexName = c.getColumnIndex(TweetColumns.NAME);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TweetViewHolder holder = (TweetViewHolder)view.getTag();
		
		holder.getImageView().setUrl(cursor.getString(indexImageUrl));
		holder.getMessageTextView().setText(cursor.getString(indexText));
		holder.getUserNameTextView().setText(cursor.getString(indexName));
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.tweet_row, null);
		
		// Create a ViewHolder and store references to the children views
		// we want to bind data to.
		TweetViewHolder holder = new TweetViewHolder();
		holder.setMessageTextView((TextView) view.findViewById(R.id.tweet_row_message));
		holder.setUserNameTextView((TextView) view.findViewById(R.id.tweet_row_user_name));
		holder.setImageView((AsyncImageView)  view.findViewById(R.id.tweet_row_image));
		
		view.setTag(holder);		
		return view;
	}

}
