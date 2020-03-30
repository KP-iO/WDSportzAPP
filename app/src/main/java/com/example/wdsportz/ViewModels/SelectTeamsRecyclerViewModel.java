package com.example.wdsportz.ViewModels;

public class SelectTeamsRecyclerViewModel {

   public String teamName;
   public String teamLogoURl;

    public SelectTeamsRecyclerViewModel () {

    }


   public SelectTeamsRecyclerViewModel (String teamName , String teamLogoURl){

        this.teamName = teamName;
        this.teamLogoURl = teamLogoURl;


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
}
