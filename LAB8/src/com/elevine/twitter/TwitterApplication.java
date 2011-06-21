package com.elevine.twitter;

import greendroid.app.GDApplication;

public class TwitterApplication extends GDApplication{
	@Override
	public Class<MainActivity> getHomeActivityClass() {
		return MainActivity.class;
	}
}
