package com.sergeivasilenko.exemplary.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Created on 18/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class ApiResponseFactory {

	private ObjectMapper mObjectMapper;

	@Inject
	public ApiResponseFactory(ObjectMapper objectMapper) {
		mObjectMapper = objectMapper;
	}

	public <T> ApiResponse<T> create(Response<T> response) {
		return new ApiResponse<>(response, mObjectMapper);
	}

	public <T> ApiResponse<T> create(Throwable t) {
		return new ApiResponse<>(t);
	}
}
