package com.sergeivasilenko.exemplary.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 23.02.17.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module
public class JacksonModule {

	@Singleton
	@Provides
	public ObjectMapper provideObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}
}
