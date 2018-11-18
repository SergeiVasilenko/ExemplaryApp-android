package com.sergeivasilenko.exemplary.di;

import com.sergeivasilenko.exemplary.Application;
import com.sergeivasilenko.exemplary.network.NetworkModule;
import com.sergeivasilenko.exemplary.repository.RepositoryModule;
import com.sergeivasilenko.exemplary.utils.JacksonModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created on 23/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Component(
		modules = {
				AndroidSupportInjectionModule.class,
				AppModule.class,
				ContributeActivityModule.class,
				JacksonModule.class,
				NetworkModule.class,
				RepositoryModule.class
		}
)
@Singleton
public interface AppComponent {

	Application application();

	void inject(Application application);
}
