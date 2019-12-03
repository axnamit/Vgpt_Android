package com.optimistic.vgpt.api_client;

import android.content.Context;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class InterceptorHTTPClientCreator {

    private static OkHttpClient defaultHttpClient;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "EVE_PREF_UNIQUE_ID";
    private static String companyId;
    private static String auth;
    private String authorization;
    private static HashMap<String, String> userDetails;

   /* private synchronized static String getUniqueId(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }*/

    public static void createInterceptorHTTPClient(final Context context){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {


                                Request request = chain.request().newBuilder()
                                        .addHeader("Content-Type", " text/xml")

//                                            .addHeader("User-Credential",SecurityUtil.base64Encode("suman.samanta@fingertipmail"+SecurityUtil.md5Encode("1234"))+"ABC")
                                        .build();
//                                    Log.i("RETROFIT_OREO", "Request: method ==> " + request.method());
//                                    Log.i("RETROFIT_OREO", "Request: url ==> " + request.url().toString());
//                                    Log.i("RETROFIT_OREO", "Request: headers ==> " + request.headers().toString());
//                                    if (request.body() != null) {
//                                        Log.i("RETROFIT_OREO", "Request: body length ==> " + request.body().contentLength());
//                                        Log.i("RETROFIT_OREO", "Request: body ==> " + request.body().toString());
//                                    }
//                                    Log.i("RETROFIT_OREO", "Request: ==> end");
                                return chain.proceed(request);
                            }
                        })
                .readTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .build();
    }






    public static boolean isHttpClientNull(){
        return defaultHttpClient == null;
    }

    static OkHttpClient getOkHttpClient(){
        if (defaultHttpClient != null){
            return defaultHttpClient;
        }
        return null;
    }
    public static void clearOkHttpClient(){
        defaultHttpClient = null;
    }

}