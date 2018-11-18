package com.sergeivasilenko.exemplary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OffersResponse {

	@JsonProperty("id")
	private String mId;

	@JsonProperty("name")
	private String mName;

	@JsonProperty("info")
	private String mInfo;

	@JsonProperty("offers")
	private List<Offer> mOffers;

	public String getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getInfo() {
		return mInfo;
	}

	public List<Offer> getOffers() {
		return mOffers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OffersResponse that = (OffersResponse) o;

		if (mId != null ? !mId.equals(that.mId) : that.mId != null) return false;
		if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
		if (mInfo != null ? !mInfo.equals(that.mInfo) : that.mInfo != null) return false;
		return mOffers != null ? mOffers.equals(that.mOffers) : that.mOffers == null;
	}

	@Override
	public int hashCode() {
		int result = mId != null ? mId.hashCode() : 0;
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		result = 31 * result + (mInfo != null ? mInfo.hashCode() : 0);
		result = 31 * result + (mOffers != null ? mOffers.hashCode() : 0);
		return result;
	}
}
