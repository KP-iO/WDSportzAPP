package ypw.app.wdsportz.ViewModels;

/**
 * Created by khrishawn
 */
public class ScoreViewModel {

    String homeTeam;
    String awayTeam;
    String homeScore;
    String awayScore;
    String homeIcon;
    String awayIcon;
    String eventDate;
    String eventId;
    String venue;

    public ScoreViewModel(String homeTeam, String awayTeam,String homeScore, String awayScore,  String homeIcon,String awayIcon, String eventDate, String eventId, String venue) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeIcon = homeIcon;
        this.awayIcon = awayIcon;
        this.eventDate = eventDate;
        this.eventId = eventId;
        this.venue = venue;
    }


    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeIcon() {
        return homeIcon;
    }

    public void setHomeIcon(String homeIcon) {
        this.homeIcon = homeIcon;
    }

    public String getAwayIcon() {
        return awayIcon;
    }

    public void setAwayIcon(String awayIcon) {
        this.awayIcon = awayIcon;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }



    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }


}

