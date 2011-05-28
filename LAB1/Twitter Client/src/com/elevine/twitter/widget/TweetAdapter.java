package com.elevine.twitter.widget;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.elevine.twitter.R;
import com.elevine.twitter.Tweet;
import com.elevine.twitter.R.id;

public class TweetAdapter extends ListAdapter<Tweet, TweetViewHolder> {

	public TweetAdapter(Context context, List<Tweet> items, int layoutId) {
		super(context, items, layoutId);
	}

	@Override
	public TweetViewHolder createViewHolder() {
		return new TweetViewHolder();
	}

	@Override
	public void populatViewHolder(TweetViewHolder holder, View convertView) {
		
		holder.setMessageTextView((TextView) convertView.findViewById(R.id.tweet_row_message));
		holder.setUserNameTextView((TextView) convertView.findViewById(R.id.tweet_row_user_name));
	}

	@Override
	public void setupViews(Tweet item, TweetViewHolder holder) {
		holder.getMessageTextView().setText(item.getText());
		holder.getUserNameTextView().setText(item.getUser().getName());
	}

}
