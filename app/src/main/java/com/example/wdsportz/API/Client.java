package com.example.wdsportz.API;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khrishawn
 */
public class Client {



        public static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=/";
        public static Retrofit retrofit = null;

        public static Retrofit getClient(){
            if (retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
}