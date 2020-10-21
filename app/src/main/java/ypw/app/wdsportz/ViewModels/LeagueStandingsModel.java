package ypw.app.wdsportz.ViewModels;

public class LeagueStandingsModel implements Comparable<LeagueStandingsModel> {

    String teamID;
    String teamRank;
    String teamName;
    String teamWins;
    String teamDraw;
    String teamLose;
    String goalsFor;
    String goalsAgainst;
    String goalDifference;
    String teamIcon;
    String teamForm;
    String Points;


    public LeagueStandingsModel(String teamID, String teamRank, String teamName, String teamWins, String teamDraw, String teamLose, String goalsFor, String goalsAgainst,
                                String goalDifference, String teamIcon, String teamForm, String Points) {

        this.teamID = teamID;
        this.teamRank = teamRank;
        this.teamName = teamName;
        this.teamWins = teamWins;
        this.teamDraw = teamDraw;
        this.teamLose = teamLose;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.teamIcon = teamIcon;
        this.teamForm = teamForm;
        this.Points = Points;
    }


    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamRank() {
        return teamRank;
    }

    public void setTeamRank(String teamRank) {
        this.teamRank = teamRank;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(String teamWins) {
        this.teamWins = teamWins;
    }

    public String getTeamDraw() {
        return teamDraw;
    }

    public void setTeamDraw(String teamDraw) {
        this.teamDraw = teamDraw;
    }

    public String getTeamLose() {
        return teamLose;
    }

    public void setTeamLose(String teamLose) {
        this.teamLose = teamLose;
    }

    public String getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(String goalsFor) {
        this.goalsFor = goalsFor;
    }

    public String getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public String getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(String goalDifference) {
        this.goalDifference = goalDifference;
    }

    public String getTeamIcon() {
        return teamIcon;
    }

    public void setTeamIcon(String teamIcon) {
        this.teamIcon = teamIcon;
    }

    public String getTeamForm() {
        return teamForm;
    }

    public void setTeamForm(String teamForm) {
        this.teamForm = teamForm;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    @Override
    public int compareTo(LeagueStandingsModel o) {
        return Integer.valueOf(this.teamRank) - Integer.valueOf(o.teamRank);
    }
}


