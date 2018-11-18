
package com.sergeivasilenko.exemplary.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

	// Client-side errors
	public static final String CLIENT_PARSE_ERROR              = "Client_PARSE_ERROR";
	public static final String CLIENT_INNER_ERROR              = "Client_INNER_ERROR";
	public static final String CLIENT_NO_INTERNET              = "Client_NO_INTERNET";
	public static final String CLIENT_UNEXPECTED_ERROR         = "Client_UNEXPECTED_ERROR";
	public static final String CLIENT_UNSUPPORTED_FORMAT       = "Client_UNSUPPORTED_FORMAT";
	public static final String CLIENT_VIDEO_TOO_BIG            = "Client_VIDEO_TOO_BIG";
	public static final String CLIENT_VIDEO_COMPRESSING_FAILED = "Client_VIDEO_COMPRESSING_FAILED";
	public static final String CLIENT_BILLING_ERROR            = "Client_BILLING_ERROR";
	public static final String SERVER_DOWN_ERROR               = "Server_DOWN_ERROR";
	public static final String SERVER_SIDE_ERROR               = "SERVER_SIDE_ERROR";

	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;

	private Error() {
	}

	@JsonIgnore
	public Error(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return The code
	 */
	@JsonIgnore
	public String getCode() {
		return code;
	}

	/**
	 * @return The message
	 */
	@JsonIgnore
	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Error error = (Error) o;

		if (code != null ? !code.equals(error.code) : error.code != null) return false;
		return message != null ? message.equals(error.message) : error.message == null;
	}

	@Override
	public int hashCode() {
		int result = code != null ? code.hashCode() : 0;
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Error{" +
				"code='" + code + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}