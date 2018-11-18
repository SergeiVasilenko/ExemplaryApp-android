package com.sergeivasilenko.exemplary.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeivasilenko.exemplary.Application;
import com.sergeivasilenko.exemplary.BuildConfig;
import com.sergeivasilenko.exemplary.network.util.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created on 18/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module
public class NetworkModule {

	private static final String TAG = "Network";

	private static final HttpLoggingInterceptor.Level REQUEST_LOG_LEVEL = BuildConfig.DEBUG ?
																		  HttpLoggingInterceptor.Level.BASIC :
																		  HttpLoggingInterceptor.Level.BASIC;

	private static final int CONNECT_TIMEOUT_MILLIS = 5 * 60_000;
	private static final int READ_TIMEOUT_MILLIS    = 5 * 60_000;

	private static final String BASE_API_PATH = "http://sandytrast.info";

	@Singleton
	@Provides
	OkHttpClient provideOkHttpClient(final Application app) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
		builder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(@NonNull String message) {
				Log.d(TAG, message);
			}
		});
		interceptor.setLevel(REQUEST_LOG_LEVEL);
		builder.addInterceptor(interceptor);

		return builder.build();
	}

	@Singleton
	@Provides
	Retrofit provideRetrofitAdapter(OkHttpClient httpClient,
									ObjectMapper objectMapper,
									ApiResponseFactory apiResponseFactory) {
		return new Retrofit.Builder()
				.baseUrl(BASE_API_PATH)
				.addConverterFactory(JacksonConverterFactory.create(objectMapper))
				.client(httpClient)
//				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addCallAdapterFactory(new LiveDataCallAdapterFactory(apiResponseFactory))
				.build();
	}

	@Singleton
	@Provides
	Api provideApi(Retrofit retrofit) {
		return retrofit.create(Api.class);
	}
}
