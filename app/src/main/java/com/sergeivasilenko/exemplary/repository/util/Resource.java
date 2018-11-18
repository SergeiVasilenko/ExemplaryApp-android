package com.sergeivasilenko.exemplary.repository.util;


import android.arch.core.util.Function;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergeivasilenko.exemplary.model.error.Error;

/**
 * Created on 26.07.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public class Resource<T> {
	@NonNull
	public final           Status status;
	@Nullable
	public final           T      data;
	@Nullable public final Error  error;

	protected Resource(@NonNull Status status, @Nullable T data, @Nullable Error error) {
		this.status = status;
		this.data = data;
		this.error = error;
	}

	public static <T> Resource<T> success(@NonNull T data) {
		return new Resource<>(Status.SUCCESS, data, null);
	}

	public static <T> Resource<T> error(Error error, @Nullable T data) {
		return new Resource<>(Status.ERROR, data, error);
	}

	public static <T> Resource<T> loading(@Nullable T data) {
		return new Resource<>(Status.LOADING, data, null);
	}

	public boolean isSuccess() {
		return status == Status.SUCCESS;
	}

	public boolean isLoading() {
		return status == Status.LOADING;
	}

	public <R> Resource<R> map(Function<T, R> mapper) {
		return new Resource<>(status, mapper.apply(data), error);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Resource<?> resource = (Resource<?>) o;

		if (status != resource.status) {
			return false;
		}
		if (error != null ? !error.equals(resource.error) : resource.error != null) {
			return false;
		}
		return data != null ? data.equals(resource.data) : resource.data == null;
	}

	@Override
	public int hashCode() {
		int result = status.hashCode();
		result = 31 * result + (error != null ? error.hashCode() : 0);
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Resource{" +
				"status=" + status +
				", error='" + error + '\'' +
				", data=" + data +
				'}';
	}
}
