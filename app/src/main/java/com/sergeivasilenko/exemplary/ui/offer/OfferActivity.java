package com.sergeivasilenko.exemplary.ui.offer;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sergeivasilenko.exemplary.R;
import com.sergeivasilenko.exemplary.model.Offer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 25/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class OfferActivity extends AppCompatActivity {

	private static final String EXTRA_OFFER_ID = "EXTRA_OFFER_ID";

	@BindView(R.id.title)
	TextView mTitleView;
	@BindView(R.id.description)
	TextView mDescriptionView;

	@Inject
	ViewModelProvider.Factory mViewModelProvider;

	private OfferViewModel mViewModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = ViewModelProviders.of(this, mViewModelProvider).get(OfferViewModel.class);
		setContentView(R.layout.activity_offer);

		ButterKnife.bind(this);

		setupViewModel();

		onNewIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		String offerId = intent.getStringExtra(EXTRA_OFFER_ID);
		mViewModel.setOfferId(offerId);
	}

	private void setupViewModel() {
		mViewModel.getOffer().observe(this, this::setOffer);
	}

	private void setOffer(Offer offer) {
		mTitleView.setText(offer.getName());
		mDescriptionView.setText(offer.getFullDescription());
	}

	public static void show(@NonNull Context context, @NonNull String offerId) {
		Intent intent = new Intent(context, OfferActivity.class);
		intent.putExtra(EXTRA_OFFER_ID, offerId);
		context.startActivity(intent);
	}
}
