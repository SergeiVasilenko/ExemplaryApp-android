package com.sergeivasilenko.exemplary.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.sergeivasilenko.exemplary.R;
import com.sergeivasilenko.exemplary.model.Offer;
import com.sergeivasilenko.exemplary.model.error.Error;
import com.sergeivasilenko.exemplary.repository.util.ResourceHandler;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.recycler)
	RecyclerView mRecyclerView;
	@BindView(R.id.progress)
	ProgressBar  mProgressBar;

	@Inject
	ViewModelProvider.Factory mViewModelProvider;

	private MainViewModel mViewModel;

	private final MainAdapter mAdapter = new MainAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = ViewModelProviders.of(this, mViewModelProvider).get(MainViewModel.class);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		loadOffers();
	}

	private void loadOffers() {
		mViewModel.getOffers().observe(this, resource ->
				ResourceHandler.of(resource)
							   .result(response -> setOffers(response.getOffers()))
							   .progress(this::setProgress)
							   .error(this::showError)
							   .handle());
	}

	private void showError(Error error) {
		new AlertDialog.Builder(this)
				.setTitle(R.string.error)
				.setMessage(error.getMessage())
				.show();
	}

	private void setProgress(boolean isProgress) {
		if (isProgress) {
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			mProgressBar.setVisibility(View.GONE);
		}
	}

	private void setOffers(List<Offer> offers) {
		mAdapter.set(offers);
	}
}
