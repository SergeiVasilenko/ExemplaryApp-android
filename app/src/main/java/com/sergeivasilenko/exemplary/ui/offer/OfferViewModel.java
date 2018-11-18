package com.sergeivasilenko.exemplary.ui.offer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.repository.OfferRepository;
import com.sergeivasilenko.exemplary.utils.AbsentLiveData;

import javax.inject.Inject;

/**
 * Created on 25/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class OfferViewModel extends ViewModel {

	private final OfferRepository mOfferRepository;

	private MutableLiveData<String> mOfferId = new MutableLiveData<>();
	private LiveData<Offer> mOffer;

	@Inject
	public OfferViewModel(OfferRepository offerRepository) {
		mOfferRepository = offerRepository;
		mOfferId.setValue("");

		mOffer = Transformations.switchMap(mOfferId, offerId -> {
			if (TextUtils.isEmpty(offerId)) {
				return AbsentLiveData.create();
			} else {
				return offerRepository.getOffer(offerId);
			}
		});
	}

	public void setOfferId(String offerId) {
		mOfferId.setValue(offerId);
	}

	public LiveData<Offer> getOffer() {
		return mOffer;
	}

}
