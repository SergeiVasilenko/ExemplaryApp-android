package com.sergeivasilenko.exemplary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer {

	@JsonProperty("id")
	private String mId;

	@JsonProperty("name")
	private String mName;

	@JsonProperty("des")
	private String mDescription;

	@JsonProperty("logo")
	private String mLogo;

	@JsonProperty("url")
	private String mUrl;

	@JsonProperty("btn")
	private String mButtonText;

	@JsonProperty("btn2")
	private String mButtonText2;

	@JsonProperty("browser")
	private boolean mBrowser;

	@JsonProperty("enabled")
	private boolean mIsEnabled;

	@JsonProperty("desc_full")
	private String mFullDescription;

	public String getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getLogo() {
		return mLogo;
	}

	public String getUrl() {
		return mUrl;
	}

	public String getButtonText() {
		return mButtonText;
	}

	public String getButtonText2() {
		return mButtonText2;
	}

	public boolean isBrowser() {
		return mBrowser;
	}

	public boolean isEnabled() {
		return mIsEnabled;
	}

	public String getFullDescription() {
		return mFullDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Offer offer = (Offer) o;

		if (mBrowser != offer.mBrowser) return false;
		if (mIsEnabled != offer.mIsEnabled) return false;
		if (mId != null ? !mId.equals(offer.mId) : offer.mId != null) return false;
		if (mName != null ? !mName.equals(offer.mName) : offer.mName != null) return false;
		if (mDescription != null ? !mDescription.equals(offer.mDescription) : offer.mDescription != null) return false;
		if (mLogo != null ? !mLogo.equals(offer.mLogo) : offer.mLogo != null) return false;
		if (mUrl != null ? !mUrl.equals(offer.mUrl) : offer.mUrl != null) return false;
		if (mButtonText != null ? !mButtonText.equals(offer.mButtonText) : offer.mButtonText != null) return false;
		if (mButtonText2 != null ? !mButtonText2.equals(offer.mButtonText2) : offer.mButtonText2 != null) return false;
		return mFullDescription != null ? mFullDescription.equals(offer.mFullDescription) : offer.mFullDescription == null;
	}

	@Override
	public int hashCode() {
		int result = mId != null ? mId.hashCode() : 0;
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
		result = 31 * result + (mLogo != null ? mLogo.hashCode() : 0);
		result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
		result = 31 * result + (mButtonText != null ? mButtonText.hashCode() : 0);
		result = 31 * result + (mButtonText2 != null ? mButtonText2.hashCode() : 0);
		result = 31 * result + (mBrowser ? 1 : 0);
		result = 31 * result + (mIsEnabled ? 1 : 0);
		result = 31 * result + (mFullDescription != null ? mFullDescription.hashCode() : 0);
		return result;
	}
}
