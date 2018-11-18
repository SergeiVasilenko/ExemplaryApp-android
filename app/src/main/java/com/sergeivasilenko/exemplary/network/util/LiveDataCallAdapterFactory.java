package com.sergeivasilenko.exemplary.network.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sergeivasilenko.exemplary.network.ApiResponse;
import com.sergeivasilenko.exemplary.network.ApiResponseFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    private final ApiResponseFactory mApiResponseFactory;

    public LiveDataCallAdapterFactory(ApiResponseFactory apiResponseFactory) {
        mApiResponseFactory = apiResponseFactory;
    }

    @Override
	public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (! (observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType, mApiResponseFactory);
    }
}