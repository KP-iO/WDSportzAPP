package com.example.wdsportz.ViewModels;

public class LeagueViewModel {

    String leagueTitle;
    String leagueImageURL;
    String leagueURL;




    public LeagueViewModel() {

    }

    public LeagueViewModel(String leagueTitle, String leagueImageURL, String leagueURL) {
        this.leagueTitle = leagueTitle;
        this.leagueImageURL = leagueImageURL;
        this.leagueURL = leagueURL;

    }








    public  String getLeagueURL() {
        return leagueURL;
    }

    public void setLeagueURL(String leagueURL) {this.leagueURL = leagueURL; }



    public String getLeagueImageURL() {
        return leagueImageURL;
    }

    public void setLeagueImageURL(String leagueImageURL) {this.leagueImageURL = leagueImageURL; }

    public String getLeagueTitle() {
        return leagueTitle;
    }

    public void setLeagueTitle(String leagueTitle) {
        this.leagueTitle = leagueTitle;
    }
}
