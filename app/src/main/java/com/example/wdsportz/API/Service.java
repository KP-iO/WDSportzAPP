package com.example.wdsportz.API;

import com.example.wdsportz.Model.LeagueResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by khrishawn
 */
public interface Service {

    @GET("eventspastleague.php?id=4647")
    Call<LeagueResponse> getEvents();

    @GET("events/strEvent")
    Call<LeagueResponse> getMatchName(@Query("1") String apiKey);

    @GET("events/strLeague")
    Call<LeagueResponse> geMatchLeague(@Query("1") String apiKey);

    @GET("events/strHomeTeam")
    Call<LeagueResponse> getHomeTeam(@Query("1") String apiKey);

    @GET("events/strAwayTeam")
    Call<LeagueResponse> getAwayTeam(@Query("1") String apiKey);

    @GET("events/intHomeScore")
    Call<LeagueResponse> getHomeScore(@Query("1") String apiKey);

    @GET("events/intAwayScore")
    Call<LeagueResponse> getAwayScore(@Query("1") String apiKey);

    @GET("events/dateEventLocal")
    Call<LeagueResponse> getDate(@Query("1") String apiKey);

    @GET("events/strTime")
    Call<LeagueResponse> getTime(@Query("1") String apiKey);

    @GET("events/idHomeTeam")
    Call<LeagueResponse> getHomeTeamId(@Query("1") String apiKey);

//    @GET("events")
//    Call<LeagueResponse> getEvents(@Query("id") String apiKey);


}
