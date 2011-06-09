package com.elevine.twitter.database.sqlite;

import com.elevine.twitter.provider.TweetColumns;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TweetOpenHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "tweets.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TWEETS_TABLE_NAME = "tweets";
	
	public TweetOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TWEETS_TABLE_NAME + " ("
				+ TweetColumns._ID + " INTEGER PRIMARY KEY,"
				+ TweetColumns.TEXT + " TEXT," 
				+ TweetColumns.NAME + " TEXT," 
				+ TweetColumns.PROFILE_IMAGE_URL + " TEXT" 
				+ ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}

}
