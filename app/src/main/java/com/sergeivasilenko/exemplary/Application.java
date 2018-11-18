package com.sergeivasilenko.exemplary;

import android.app.Activity;

import com.sergeivasilenko.exemplary.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created on 17/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class Application extends android.app.Application implements HasActivityInjector {

	public static final String ID = "1";

	@Inject
	DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

	@Override
	public void onCreate() {
		super.onCreate();
		AppInjector.init(this);
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return mDispatchingAndroidInjector;
	}
}
