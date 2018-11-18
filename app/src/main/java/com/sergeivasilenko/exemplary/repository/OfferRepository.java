package com.sergeivasilenko.exemplary.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.model.OffersResponse;
import com.sergeivasilenko.exemplary.repository.util.Resource;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public interface OfferRepository {
	LiveData<Resource<OffersResponse>> getOfferList(@NonNull String id);

	LiveData<Offer> getOffer(@NonNull String id);
}
