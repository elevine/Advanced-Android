package com.elevine.twitter.widget;

import greendroid.widget.AsyncImageView;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetViewHolder {
	TextView messageTextView = null;
	TextView userNameTextView = null;
	AsyncImageView imageView = null;

	public TextView getMessageTextView() {
		return messageTextView;
	}

	public void setMessageTextView(TextView messageTextView) {
		this.messageTextView = messageTextView;
	}

	public TextView getUserNameTextView() {
		return userNameTextView;
	}

	public void setUserNameTextView(TextView userNameTextView) {
		this.userNameTextView = userNameTextView;
	}

	public AsyncImageView getImageView() {
		return imageView;
	}

	public void setImageView(AsyncImageView imageView) {
		this.imageView = imageView;
	}
}
