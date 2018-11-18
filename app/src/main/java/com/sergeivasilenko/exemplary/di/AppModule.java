package com.sergeivasilenko.exemplary.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sergeivasilenko.exemplary.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 17/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module(includes = ViewModelModule.class)
public class AppModule {

	private Application mApp;

	public AppModule(@NonNull Application app) {
		mApp = app;
	}

	@Provides
	@Singleton
	Application provideApplication() {
		return mApp;
	}

	@Provides
	@Singleton
	Context provideContext() {
		return mApp;
	}
}
