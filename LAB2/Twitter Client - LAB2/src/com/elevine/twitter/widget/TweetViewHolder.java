package com.elevine.twitter.widget;

import android.widget.TextView;

public class TweetViewHolder {
	TextView messageTextView = null;
	TextView userNameTextView = null;

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

}
