package com.sergeivasilenko.exemplary.network;

import android.arch.core.util.Function;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergeivasilenko.exemplary.model.error.Error;
import com.sergeivasilenko.exemplary.network.util.RequestException;
import com.sergeivasilenko.exemplary.utils.log.LogManager;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created on 27.07.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public class ApiResponse<T> {

	private static final LogManager log = new LogManager("ApiResponse");

	public final int       code;
	@Nullable
	public final T         body;
	@Nullable
	public final Error     error;
	@Nullable
	public final Throwable throwable;

	public ApiResponse(Throwable t) {
		code = 500;
		body = null;
		error = errorFromThrowable(t);
		throwable = t;
	}

	public ApiResponse(Response<T> response, ObjectMapper mapper) {
		code = response.code();
		if (response.isSuccessful()) {
			body = response.body();
			error = null;
		} else {
			body = null;
			error = errorFromResponse(response, mapper);
		}
		throwable = null;
	}

	private ApiResponse(int code, @Nullable T body, @Nullable Error error) {
		this.code = code;
		this.body = body;
		this.error = error;
		this.throwable = null;
	}

	public boolean isSuccessful() {
		return code >= 200 && code < 300;
	}

	public <R> ApiResponse<R> cast(Function<T, R> caster) {
		return new ApiResponse<>(code, body == null ? null : caster.apply(body), error);
	}

	private Error errorFromThrowable(Throwable t) {
		Error error;
		if (t instanceof RequestException) {
			error = ((RequestException) t).getError();
		} else if (t instanceof IOException) {
			if (t instanceof SocketTimeoutException) {
				error = new Error(Error.SERVER_DOWN_ERROR, "Connection interrupted by timeout: " + t.getMessage());
			} else if (t instanceof JsonMappingException) {
				log.e("parse error: ", t);
				error = new Error(Error.CLIENT_PARSE_ERROR, "There is parsing error");
			} else /*if (t instanceof ConnectException)*/ {
				error = new Error(Error.CLIENT_NO_INTERNET, "No internet");
			}
		} else {
			log.e("Error in request", t);
			error = new Error(Error.CLIENT_INNER_ERROR, t.getMessage());
		}
		return error;
	}

	private Error errorFromResponse(Response<T> response, ObjectMapper mapper) {
		int code = response.code();
		if (code / 100 == 5) {
			log.w("server side error: %d", response.code());
			return new Error(Error.SERVER_SIDE_ERROR, "Something went wrong");
		}
		ResponseBody body = response.errorBody();
		try {
			String message = null;
			if (body != null) {
				message = body.string();
			}
			return new Error(String.valueOf(code), "Error [" + code + "] : " + message);
		} catch (IOException e) {
			log.e("Error parse error", e);
			return new Error(Error.CLIENT_PARSE_ERROR, e.getMessage());
		}
	}

}
