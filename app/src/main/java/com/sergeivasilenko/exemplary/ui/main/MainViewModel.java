package com.sergeivasilenko.exemplary.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sergeivasilenko.exemplary.Application;
import com.sergeivasilenko.exemplary.model.OffersResponse;
import com.sergeivasilenko.exemplary.repository.OfferRepository;
import com.sergeivasilenko.exemplary.repository.util.Resource;

import javax.inject.Inject;

/**
 * Created on 23/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class MainViewModel extends ViewModel {

	private final OfferRepository mOfferRepository;

	@Inject
	public MainViewModel(OfferRepository offerRepository) {
		mOfferRepository = offerRepository;
	}

	public LiveData<Resource<OffersResponse>> getOffers() {
		return mOfferRepository.getOfferList(Application.ID);
	}
}
