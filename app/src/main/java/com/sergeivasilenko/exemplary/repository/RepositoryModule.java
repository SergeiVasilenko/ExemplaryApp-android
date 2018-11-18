package com.sergeivasilenko.exemplary.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
@Module
public class RepositoryModule {

	@Singleton
	@Provides
	public OfferRepository provideOffersRepository(OfferRepositoryImpl offerRepository) {
		return offerRepository;
	}
}
