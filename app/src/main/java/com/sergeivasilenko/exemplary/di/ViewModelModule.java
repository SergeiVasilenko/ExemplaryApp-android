package com.sergeivasilenko.exemplary.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sergeivasilenko.exemplary.ui.main.MainViewModel;
import com.sergeivasilenko.exemplary.ui.offer.OfferViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created on 17/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module
public abstract class ViewModelModule {

	@Binds
	abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

	@Binds
	@IntoMap
	@ViewModelKey(MainViewModel.class)
	abstract ViewModel bindMainViewModel(MainViewModel viewModel);

	@Binds
	@IntoMap
	@ViewModelKey(OfferViewModel.class)
	abstract ViewModel bindOfferViewModel(OfferViewModel viewModel);
}
