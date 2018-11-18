package com.sergeivasilenko.exemplary.network;

import android.arch.lifecycle.LiveData;

import com.sergeivasilenko.exemplary.model.OffersResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 19/09/2018.
 *
 * @author Sergey Vasilenko (vasilenko.sn@gmail.com)
 */
public interface Api {

	@GET("/ins/")
	LiveData<ApiResponse<OffersResponse>> getOffers(@Query("id") String id);
}
