package com.elevine.twitter;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TweetView extends RelativeLayout {
	
	TextView messageTextView = null;
	TextView userNameTextView = null;
	ImageView imageView = null;
	
	public TweetView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.tweet_row, this);

		messageTextView = (TextView) findViewById(R.id.tweet_row_message);
		userNameTextView = (TextView) findViewById(R.id.tweet_row_user_name);

	}
	
	public TweetView(Context context, String message, String userName, String imageUrl) {
		this(context);
		setMessage(message);
		setUserName(userName);
	}
	
	public void setMessage(String message){
		messageTextView.setText(message);
	}
	
	public void setUserName(String userName){
		userNameTextView.setText(userName);
	}
	
}
