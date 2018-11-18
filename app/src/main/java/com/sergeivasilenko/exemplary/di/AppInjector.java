package com.sergeivasilenko.exemplary.di;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.sergeivasilenko.exemplary.Application;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created on 25/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class AppInjector {

	public static AppComponent init(Application application) {
		AppComponent appComponent = DaggerAppComponent.builder()
													  .appModule(new AppModule(application))
													  .build();
		appComponent.inject(application);
		application.registerActivityLifecycleCallbacks(new android.app.Application.ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				handleActivity(activity);
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {

			}

			@Override
			public void onActivityPaused(Activity activity) {

			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {

			}
		});
		return appComponent;
	}

	private static void handleActivity(Activity activity) {
		AndroidInjection.inject(activity);
		if (activity instanceof FragmentActivity) {
			((FragmentActivity) activity).getSupportFragmentManager()
										 .registerFragmentLifecycleCallbacks(
												 new FragmentManager.FragmentLifecycleCallbacks() {
													 @Override
													 public void onFragmentCreated(FragmentManager fm, Fragment f,
																				   Bundle savedInstanceState) {
														 if (f instanceof Injectable) {
															 AndroidSupportInjection.inject(f);
														 }
													 }
												 }, true);
		}
	}
}
