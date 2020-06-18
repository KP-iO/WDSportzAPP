package com.example.wdsportz.ViewModels;

public class SelectTeamsRecyclerViewModel {

   public String teamName;
   public String teamLogoURl;
   public String teamId;




   public SelectTeamsRecyclerViewModel (String teamName , String teamLogoURl, String teamId){

        this.teamName = teamName;
        this.teamLogoURl = teamLogoURl;
        this.teamId = teamId;


    }



    public String getText1() {
       return teamName;

    }




    public  String getTeamName() {
        return teamName;
    }

    public void setTeamName(String newsDesc) {this.teamName = teamName; }

    public  String getNewsDesc() { return teamLogoURl; }

    public void setNewsDesc(String newsDesc) {this.teamLogoURl = teamLogoURl;; }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
