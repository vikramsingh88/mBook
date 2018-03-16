package vik.com.mbooks.retrofit;

/**
 * Created by M1032130 on 1/8/2018.
 */

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class AppDataClient {

    private static final long CONNECTION_TIMEOUT = 30;

    private final IAppDataAPIs mClient;

    private static class SingletonHolder {
        private static final AppDataClient INSTANCE = new AppDataClient();
    }

    private AppDataClient() {

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(IAppDataAPIs.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header(IAppDataAPIs.CONTENT_TYPE, IAppDataAPIs.JSON_TYPE);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        mClient = retrofit.create(IAppDataAPIs.class);
    }

    public static IAppDataAPIs getClient() {
        return SingletonHolder.INSTANCE.mClient;
    }
}
