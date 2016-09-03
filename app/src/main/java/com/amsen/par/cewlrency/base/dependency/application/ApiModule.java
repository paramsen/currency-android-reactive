package com.amsen.par.cewlrency.base.dependency.application;

import com.amsen.par.cewlrency.BuildConfig;
import com.amsen.par.cewlrency.api.fixerio.response.CurrencyExchangeRatesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Pär Amsen 2016
 */
@Module
public class ApiModule {
    protected static OkHttpClient.Builder okHttpBuilder(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        if (BuildConfig.DEBUG) {
            try {
                builder.addInterceptor(new DebugLoggingInterceptor());

                return builder;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return builder;
    }

    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://api.fixer.io")
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        if(type == CurrencyExchangeRatesResponse.class) {
                            return value -> CurrencyExchangeRatesResponse.parse(value.byteStream());
                        }

                        return null; //leave conversion to GSON
                    }
                })
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpBuilder().build())
                .build();
    }

    /**
     * http://stackoverflow.com/a/32075121/5704677
     *
     * @deprecated exposed for debug builds.
     */
    protected static class DebugLoggingInterceptor implements Interceptor {

        private static final String F_BREAK = " %n";
        private static final String F_URL = " %s";
        private static final String F_TIME = " in %.1fms";
        private static final String F_HEADERS = "%s";
        private static final String F_RESPONSE = F_BREAK + "Response: %d";
        private static final String F_BODY = "body: %s";

        private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
        private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
        private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
        private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
        private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;

        private static String stringifyRequestBody(Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            MediaType contentType = null;
            String bodyString = null;
            if (response.body() != null) {
                contentType = response.body().contentType();
                bodyString = response.body().string();
            }

            double time = (t2 - t1) / 1e6d;

            System.out.println();
            System.out.println();

            if (request.method().equals("GET")) {
                System.out.println(String.format("GET " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), response.code(), response.headers(), stringifyResponseBody(bodyString)));
            } else if (request.method().equals("POST")) {
                System.out.println(String.format("POST " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), stringifyRequestBody(request), response.code(), response.headers(), stringifyResponseBody(bodyString)));
            } else if (request.method().equals("PUT")) {
                System.out.println(String.format("PUT " + F_REQUEST_WITH_BODY + F_RESPONSE_WITH_BODY, request.url(), time, request.headers(), request.body().toString(), response.code(), response.headers(), stringifyResponseBody(bodyString)));
            } else if (request.method().equals("DELETE")) {
                System.out.println(String.format("DELETE " + F_REQUEST_WITHOUT_BODY + F_RESPONSE_WITHOUT_BODY, request.url(), time, request.headers(), response.code(), response.headers()));
            }

            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(contentType, bodyString);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }

        public String stringifyResponseBody(String responseBody) {
            return responseBody;
        }

    }
}
