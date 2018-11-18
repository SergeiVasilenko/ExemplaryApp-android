package com.sergeivasilenko.exemplary.network.util;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sergeivasilenko.exemplary.network.ApiResponse;
import com.sergeivasilenko.exemplary.network.ApiResponseFactory;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapterthat converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
	private final Type responseType;

	private final ApiResponseFactory mApiResponseFactory;

    public LiveDataCallAdapter(Type responseType, ApiResponseFactory apiResponseFactory) {
        this.responseType = responseType;
        mApiResponseFactory = apiResponseFactory;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(@NonNull Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                            postValue(mApiResponseFactory.create(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                            postValue(mApiResponseFactory.create(throwable));
                        }
                    });
                }
            }
        };
    }
}