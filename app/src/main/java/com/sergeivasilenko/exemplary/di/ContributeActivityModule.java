package com.sergeivasilenko.exemplary.di;

import com.sergeivasilenko.exemplary.ui.main.MainActivity;
import com.sergeivasilenko.exemplary.ui.offer.OfferActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created on 25/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module
public abstract class ContributeActivityModule {

	@ContributesAndroidInjector()
	abstract MainActivity contributeMainActivity();

	@ContributesAndroidInjector()
	abstract OfferActivity contributeOfferActivity();
}
