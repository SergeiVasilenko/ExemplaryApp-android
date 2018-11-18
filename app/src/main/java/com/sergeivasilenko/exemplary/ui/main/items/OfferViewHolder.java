package com.sergeivasilenko.exemplary.ui.main.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergeivasilenko.exemplary.R;
import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.ui.offer.OfferActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 23/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class OfferViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.image)
	ImageView mImageView;

	@BindView(R.id.title)
	TextView mTitleView;

	@BindView(R.id.description)
	TextView mDescriptionView;

	@BindView(R.id.show)
	Button mShowButton;

	private Offer mOffer;

	public OfferViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void bind(@NonNull Offer offer) {
		mOffer = offer;
		if (offer.getLogo() != null) {
			Picasso.get().load(offer.getLogo()).into(mImageView);
		} else {
			mImageView.setImageDrawable(null);
		}
		mTitleView.setText(offer.getName());
		mDescriptionView.setText(offer.getDescription());
		mShowButton.setOnClickListener(v -> showOffer());
	}

	private void showOffer() {
		OfferActivity.show(itemView.getContext(), mOffer.getId());
	}
}
