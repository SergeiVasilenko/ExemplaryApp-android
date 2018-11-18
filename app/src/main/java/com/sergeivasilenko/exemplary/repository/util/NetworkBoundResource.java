package com.sergeivasilenko.exemplary.repository.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sergeivasilenko.exemplary.network.ApiResponse;
import com.sergeivasilenko.exemplary.utils.AppExecutors;

import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Supplier;


/**
 * Created on 27.07.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

	private final AppExecutors mAppExecutors;

	private final MediatorLiveData<Resource<ResultType>> mResult = new MediatorLiveData<>();

	@MainThread
	NetworkBoundResource(AppExecutors appExecutors) {
		mAppExecutors = appExecutors;
		mResult.setValue(Resource.loading(null));
		LiveData<ResultType> dbSource = loadFromDb();
		mResult.addSource(dbSource, data -> {
			mResult.removeSource(dbSource);
			if (shouldFetch(data)) {
				fetchFromNetwork(dbSource);
			} else {
				mResult.addSource(dbSource, newData -> mResult.setValue(Resource.success(newData)));
			}
		});
	}

	private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
		mResult.addSource(dbSource, data -> mResult.setValue(Resource.loading(data)));
		mAppExecutors.networkIO().execute(() -> {
			LiveData<ApiResponse<RequestType>> apiResponse = createCall();
			if (apiResponse == null) {
				throw new NullPointerException("Null from createCall: " + this);
			}
			mAppExecutors.mainThread().execute(() -> {
				mResult.addSource(apiResponse, response -> {
					mResult.removeSource(dbSource);
					mResult.removeSource(apiResponse);
					handleResponse(dbSource, response);
				});
			});
		});

	}

	@MainThread
	private void handleResponse(LiveData<ResultType> dbSource,
								ApiResponse<RequestType> response) {
		if (response.isSuccessful()) {
			mAppExecutors.diskIO().execute(() -> {
				saveCallResult(processResponse(response));
				mAppExecutors.mainThread().execute(() -> {
					// we specially request a new live data,
					// otherwise we will get immediately last cached value,
					// which may not be updated with latest results received from network.
					mResult.addSource(loadFromDb(), newData -> mResult.setValue(Resource.success(newData)));
				});
			});
		} else {
			onFetchFailed();
			mResult.addSource(dbSource,
							  newData -> mResult.setValue(Resource.error(response.error, newData)));
		}
	}

	protected void onFetchFailed() {

	}

	public LiveData<Resource<ResultType>> asLiveData() {
		return mResult;
	}

	@WorkerThread
	protected RequestType processResponse(ApiResponse<RequestType> response) {
		return response.body;
	}

	@WorkerThread
	protected abstract void saveCallResult(@NonNull RequestType item);

	@MainThread
	protected abstract boolean shouldFetch(ResultType data);

	@NonNull
	@MainThread
	protected abstract LiveData<ResultType> loadFromDb();

	@NonNull
	@WorkerThread
	protected abstract LiveData<ApiResponse<RequestType>> createCall();

	public static <ResultType, RequestType> Builder<ResultType, RequestType>
	create(AppExecutors appExecutors,
		   Class<ResultType> resultTypeClass,
		   Class<RequestType> requestTypeClass) {
		return new Builder<>(appExecutors);
	}

	public static <ResultType, RequestType> Builder<ResultType, RequestType>
	create(AppExecutors appExecutors,
		   TypeReference<ResultType> resultTypeClass,
		   TypeReference<RequestType> requestTypeClass) {
		return new Builder<>(appExecutors);
	}

	public static class Builder<ResultType, RequestType> {
		private AppExecutors mAppExecutors;

		private Consumer<RequestType> mSaveCallAction;

		private Function<ResultType, Boolean> mShouldFetchFunction;

		private Supplier<LiveData<ResultType>> mLoadFromDbSupplier;

		private Supplier<LiveData<ApiResponse<RequestType>>> mApiSupplier;

		private Runnable mOnFailAction;

		public Builder(@NonNull AppExecutors appExecutors) {
			mAppExecutors = appExecutors;
		}

		public Builder<ResultType, RequestType> saveCallResult(Consumer<RequestType> saveCallAction) {
			mSaveCallAction = saveCallAction;
			return this;
		}

		public Builder<ResultType, RequestType> shouldFetch(Function<ResultType, Boolean> shouldFetchFunction) {
			mShouldFetchFunction = shouldFetchFunction;
			return this;
		}

		public Builder<ResultType, RequestType> loadFromDb(Supplier<LiveData<ResultType>> loadFromDbSupplier) {
			mLoadFromDbSupplier = loadFromDbSupplier;
			return this;
		}

		public Builder<ResultType, RequestType> createCall(Supplier<LiveData<ApiResponse<RequestType>>> apiSupplier) {
			mApiSupplier = apiSupplier;
			return this;
		}

		public Builder<ResultType, RequestType> onFail(Runnable onFailAction) {
			mOnFailAction = onFailAction;
			return this;
		}

		public NetworkBoundResource<ResultType, RequestType> build() {
			if (mSaveCallAction == null) {
				throw new NullPointerException();
			}
			if (mShouldFetchFunction == null) {
				mShouldFetchFunction = data -> true;
			}
			if (mLoadFromDbSupplier == null) {
				throw new NullPointerException();
			}
			if (mApiSupplier == null) {
				throw new NullPointerException();
			}
			return new NetworkBoundResource<ResultType, RequestType>(mAppExecutors) {

				@Override
				protected void saveCallResult(@NonNull RequestType item) {
					mSaveCallAction.accept(item);
				}

				@Override
				protected boolean shouldFetch(@NonNull ResultType data) {
					return mShouldFetchFunction.apply(data);
				}

				@NonNull
				@Override
				protected LiveData<ResultType> loadFromDb() {
					return mLoadFromDbSupplier.get();
				}

				@NonNull
				@Override
				protected LiveData<ApiResponse<RequestType>> createCall() {
					LiveData<ApiResponse<RequestType>> result = mApiSupplier.get();
					if (result == null) {
						throw new NullPointerException("createCall produced null from " + mApiSupplier);
					}
					return result;
				}

				@Override
				protected void onFetchFailed() {
					super.onFetchFailed();
					if (mOnFailAction != null) {
						mOnFailAction.run();
					}
				}
			};
		}
	}
}
