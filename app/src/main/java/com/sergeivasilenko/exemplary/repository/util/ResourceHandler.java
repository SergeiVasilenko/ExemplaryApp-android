package com.sergeivasilenko.exemplary.repository.util;

import android.support.annotation.Nullable;

import com.sergeivasilenko.exemplary.model.error.Error;

import java8.util.function.Consumer;

/**
 * Created on 28.07.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public class ResourceHandler<T> {

	private final Resource<T> mResource;

	private Consumer<Boolean> mProgressConsumer;
	private Consumer<Error>   mErrorConsumer;
	private Consumer<T>       mResultConsumer;

	private ResourceHandler(@Nullable Resource<T> resource) {
		mResource = resource;
	}

	public static <T> ResourceHandler<T> of(@Nullable Resource<T> resource) {
		return new ResourceHandler<T>(resource);
	}

	public ResourceHandler<T> progress(Consumer<Boolean> consumer) {
		mProgressConsumer = consumer;
		return this;
	}

	public ResourceHandler<T> error(Consumer<Error> consumer) {
		mErrorConsumer = consumer;
		return this;
	}

	public ResourceHandler<T> result(Consumer<T> consumer) {
		mResultConsumer = consumer;
		return this;
	}

	public void handle() {
		if (mResource == null) {
			return;
		}
		switch (mResource.status) {
			case SUCCESS:
				setProgress(false);
				setResult(mResource.data);
				break;
			case ERROR:
				setProgress(false);
				setError(mResource.error);
				break;
			case LOADING:
				setProgress(true);
				if (mResource.data != null) {
					setResult(mResource.data);
				}
				break;
		}
	}

	private void setProgress(boolean progress) {
		if (mProgressConsumer != null) {
			mProgressConsumer.accept(progress);
		}
	}

	private void setError(Error error) {
		if (mErrorConsumer != null) {
			mErrorConsumer.accept(error);
		}
	}

	private void setResult(T result) {
		if (mResultConsumer != null) {
			mResultConsumer.accept(result);
		}
	}
}
