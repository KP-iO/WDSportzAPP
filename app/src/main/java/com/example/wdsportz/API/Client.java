package com.example.wdsportz.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khrishawn
 */
public class Client {
//https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4647
        public static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php/";
        public static Retrofit retrofit = null;

        public static Retrofit getClient(){

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

            return retrofit;
        }
}