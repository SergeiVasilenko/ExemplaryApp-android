
package com.sergeivasilenko.exemplary.model.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

	@JsonProperty("error")
	private Error error;

	/**
	 * @return The error
	 */
	public Error getError() {
		return error;
	}
}
