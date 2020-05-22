package com.example.wdsportz.ViewModels;

/**
 * Created by khrishawn
 */
public class FavouriteTeamsViewModel {
    private String team;
            private String teamImg;



//  teamImg  teamImgpublic FavouriteTeamsViewModel(String team, String teamImg) {
//        this.team = team;
//        this.teamImg = teamImg;
//    }
    public FavouriteTeamsViewModel(String team, String teamImg) {
        this.team = team;
        this.teamImg = teamImg;

    }


    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamImg() {
        return teamImg;
    }

    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }



}
