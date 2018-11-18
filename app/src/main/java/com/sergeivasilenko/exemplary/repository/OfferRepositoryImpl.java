package com.sergeivasilenko.exemplary.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.model.OffersResponse;
import com.sergeivasilenko.exemplary.network.Api;
import com.sergeivasilenko.exemplary.repository.util.MemoryCache;
import com.sergeivasilenko.exemplary.repository.util.NetworkBoundResource;
import com.sergeivasilenko.exemplary.repository.util.Resource;
import com.sergeivasilenko.exemplary.utils.AppExecutors;

import javax.inject.Inject;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class OfferRepositoryImpl implements OfferRepository {

	private Api mApi;
	private AppExecutors mAppExecutors;

	private MemoryCache<String, Offer> mOffersCache = new MemoryCache<>();
	private MemoryCache<String, OffersResponse> mOffersResponseCache = new MemoryCache<>();

	@Inject
	public OfferRepositoryImpl(Api api, AppExecutors appExecutors) {
		mApi = api;
		mAppExecutors = appExecutors;
	}

	@Override
	public LiveData<Resource<OffersResponse>> getOfferList(@NonNull String id) {
		return new NetworkBoundResource.Builder<OffersResponse, OffersResponse>(mAppExecutors)
				.loadFromDb(() -> mOffersResponseCache.get(id))
				.createCall(() -> mApi.getOffers(id))
				.saveCallResult(this::saveOffersResponse)
				.build()
				.asLiveData();
	}

	private void saveOffersResponse(OffersResponse offersResponse) {
		mOffersResponseCache.put(offersResponse.getId(), offersResponse);
		for (Offer offer : offersResponse.getOffers()) {
			mOffersCache.put(offer.getId(), offer);
		}
	}

	@Override
	public LiveData<Offer> getOffer(@NonNull String id) {
		return mOffersCache.get(id);
	}
}
