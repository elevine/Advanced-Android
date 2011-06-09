package com.elevine.twitter.provider;

import com.elevine.twitter.database.sqlite.TweetOpenHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TweetProvider extends ContentProvider {
	public static final String AUTHORITY = "com.elevine.twitter.provider.TweetProvider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/tweets");
	private static final UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	private static final int TWEET = 1;
	private static final int TWEET_ID = 2;
	static {
		uriMatcher.addURI(AUTHORITY, "tweets", TWEET);
		uriMatcher.addURI(AUTHORITY, "tweets/#", TWEET_ID);
	}

	private TweetOpenHelper dbHelper = null;

	@Override
	public boolean onCreate() {
		this.dbHelper = new TweetOpenHelper(getContext());
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int result = db.delete(TweetOpenHelper.TWEETS_TABLE_NAME, selection, selectionArgs);	
		getContext().getContentResolver().notifyChange(uri, null);
		return result;
	}

	@Override
	public String getType(Uri uri) {
		int type = uriMatcher.match(uri);
		switch (type) {
		case TWEET:
			return "vnd.android.cursor.dir/vnd." + AUTHORITY;
		case TWEET_ID:
			return "vnd.android.cursor.item/vnd." + AUTHORITY;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.insert(TweetOpenHelper.TWEETS_TABLE_NAME, null, values);
		getContext().getContentResolver().notifyChange(uri, null);
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TweetOpenHelper.TWEETS_TABLE_NAME);

		int type = uriMatcher.match(uri);
		switch (type) {
		case TWEET:
			break;
		case TWEET_ID:
			qb.appendWhere(TweetColumns._ID + "="
					+ uri.getPathSegments().get(1));
			break;
		default:
			return null;
		}

		return qb.query(dbHelper.getReadableDatabase(), projection, selection,
				selectionArgs, null, null, sortOrder);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		getContext().getContentResolver().notifyChange(uri, null);
		return 0;
	}

}
