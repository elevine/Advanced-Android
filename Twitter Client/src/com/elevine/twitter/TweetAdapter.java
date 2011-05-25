package com.elevine.twitter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TweetAdapter extends BaseAdapter {
	private Context context = null;
	private List<Tweet> tweets = null;
	
	public TweetAdapter(Context context, List<Tweet> tweets){
		this.context = context;
		this.tweets = tweets;
	}
	
	@Override
	public int getCount() {
		return tweets.size();
	}

	@Override
	public Object getItem(int position) {
		return tweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = new TweetView(context);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.item = (TweetView)convertView;
            convertView.setTag(holder);
            
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }
        Tweet tweet = tweets.get(position);

       
        holder.item.setMessage(tweet.getMessage());
        holder.item.setUserName(tweet.getUsername());
               
        return convertView;
	}
	
	 static class ViewHolder {
         TweetView item;
     }
}
