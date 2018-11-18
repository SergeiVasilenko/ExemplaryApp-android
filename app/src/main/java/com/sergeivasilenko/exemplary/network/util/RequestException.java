package com.sergeivasilenko.exemplary.network.util;

import com.sergeivasilenko.exemplary.model.error.Error;

import java.io.IOException;

/**
 * Created on 28.08.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */

public class RequestException extends IOException {

	private Error mError;

	public RequestException(Error error) {
		super(error.getMessage());
		mError = error;
	}

	public Error getError() {
		return mError;
	}
}
