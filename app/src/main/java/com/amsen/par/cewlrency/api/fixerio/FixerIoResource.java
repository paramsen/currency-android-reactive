package com.amsen.par.cewlrency.api.fixerio;

import com.amsen.par.cewlrency.api.fixerio.response.ExchangeRateResponse;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Pär Amsen 2016
 */
public class FixerIoResource {
    protected interface ApiInterface {
        @GET("latest")
        Observable<Response<ExchangeRateResponse>> getExchangeRates(@Query("base") String base);
    }

    public static class ApiAccess {
        private ApiInterface api;

        @Inject
        public ApiAccess(Retrofit rf) {
            api = rf.create(ApiInterface.class);
        }

        public Observable<Response<ExchangeRateResponse>> getExchangeRates() {
            return api.getExchangeRates("SEK")
                    .subscribeOn(Schedulers.io());
        }
    }
}