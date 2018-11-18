package com.sergeivasilenko.exemplary.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeivasilenko.exemplary.R;
import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.ui.main.items.OfferViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created on 25/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public class MainAdapter extends RecyclerView.Adapter<OfferViewHolder> {

	private List<Offer> mData = Collections.emptyList();

	public void set(List<Offer> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.offer_item, parent, false);
		return new OfferViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
		holder.bind(mData.get(position));
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}
}
