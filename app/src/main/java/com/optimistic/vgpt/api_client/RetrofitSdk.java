package com.optimistic.vgpt.api_client;

import android.content.Context;
import com.optimistic.vgpt.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSdk {
    private final Retrofit retrofit;
    private Service service;

    private RetrofitSdk(Retrofit retrofit) {
        this.retrofit = retrofit;
        createService();
    }

    /**
     * Builder for {@link RetrofitSdk}
     */
    public static class Builder {
        public Builder() {
        }

        /**
         * Create the {@link RetrofitSdk} to be used.
         *
         * @return {@link RetrofitSdk}
         */
        public RetrofitSdk build(Context context) {
            Retrofit retrofit = null;
            String baseUrlw="http://ec2-18-218-155-35.us-east-2.compute.amazonaws.com/vgpt/api/";
            //String baseUrl = context.getResources().getString(R.string.base_url);
            if (InterceptorHTTPClientCreator.getOkHttpClient() != null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrlw)
                        .build();

                return new RetrofitSdk(retrofit);
            } else {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrlw)
                        .build();
            }
            return new RetrofitSdk(retrofit);
        }
    }

    private void createService() {
        service = retrofit.create(Service.class);
    }

    public Service getService(){
        return service;
    }
}