package ypw.app.wdsportz.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khrishawn
 */

// Used to get Api categorie results   https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php/?id=4647


public class League implements Parcelable {
   @SerializedName("idEvent")
   private Integer idEvent;
    @SerializedName("idSoccerXML")
    private String idSoccerXML;
    @SerializedName("idAPIfootball")
    private Integer idAPIfootball;
    @SerializedName("strEvent")
    private String strEvent;
    @SerializedName("strEventAlternate")
    private String strEventAlternate;
    @SerializedName("strFilename")
    private String strFilename;
    @SerializedName("strSport")
    private String strSport;
    @SerializedName("idLeague")
    private String idLeague;
    @SerializedName("strLeague")
    private String strLeague;
    @SerializedName("strSeason")
    private String strSeason;
    @SerializedName("strDescriptionEN")
    private String strDescriptionEN;
    @SerializedName("strHomeTeam")
    private String strHomeTeam;
    @SerializedName("strAwayTeam")
    private String strAwayTeam;
    @SerializedName("intHomeScore")
    private Integer intHomeScore;
    @SerializedName("intRound")
    private Integer intRound;
    @SerializedName("intAwayScore")
    private Integer intAwayScore;
    @SerializedName("intSpectators")
    private Integer intSpectators;
    @SerializedName("strHomeGoalDetails")
    private String strHomeGoalDetails;
    @SerializedName("strHomeRedCards")
    private String strHomeRedCards;
    @SerializedName("strHomeYellowCards")
    private String strHomeYellowCards;
    @SerializedName("strHomeLineupGoalkeeper")
    private String trHomeLineupGoalkeeper;
    @SerializedName("strHomeLineupDefense")
    private String strHomeLineupDefense;
    @SerializedName("strHomeLineupMidfield")
    private String strHomeLineupMidfield;
    @SerializedName("strHomeLineupForward")
    private String strHomeLineupForward;
    @SerializedName("strHomeLineupSubstitutes")
    private String strHomeLineupSubstitutes;
    @SerializedName("strHomeFormation")
    private String strHomeFormation;
    @SerializedName("strAwayRedCards")
    private String strAwayRedCards;
    @SerializedName("strAwayYellowCards")
    private String strAwayYellowCards;
    @SerializedName("strAwayGoalDetails")
    private String strAwayGoalDetails;
    @SerializedName("strAwayLineupGoalkeeper")
    private String strAwayLineupGoalkeeper;
    @SerializedName("strAwayLineupDefense")
    private String strAwayLineupDefense;
    @SerializedName("strAwayLineupMidfield")
    private String strAwayLineupMidfield;
    @SerializedName("strAwayLineupForward")
    private String strAwayLineupForward;
    @SerializedName("strAwayLineupSubstitutes")
    private String strAwayLineupSubstitutes;
    @SerializedName("strAwayFormation")
    private String strAwayFormation;
    @SerializedName("intHomeShots")
    private Integer intHomeShots;
    @SerializedName("intAwayShots")
    private Integer intAwayShots;
    @SerializedName("dateEvent")
    private String dateEvent;
    @SerializedName("dateEventLocal")
    private String dateEventLocal;
    @SerializedName("strDate")
    private String strDate;
    @SerializedName("strTime")
    private String strTime;
    @SerializedName("strTimeLocal")
    private String strTimeLocal;
    @SerializedName("strTVStation")
    private String strTVStation;
    @SerializedName("idHomeTeam")
    private Integer idHomeTeam;
    @SerializedName("idAwayTeam")
    private Integer idAwayTeam;
    @SerializedName("strResult")
    private String strResult;
    @SerializedName("strCircuit")
    private String strCircuit;
    @SerializedName("strCountry")
    private String strCountry;
    @SerializedName("strCity")
    private String strCity;
    @SerializedName("strPoster")
    private String strPoster;
    @SerializedName("strFanart")
    private String strFanart;
    @SerializedName("strThumb")
    private String strThumb;
    @SerializedName("strBanner")
    private String strBanner;
    @SerializedName("strMap")
    private String strMap;
    @SerializedName("strTweet1")
    private String strTweet1;
    @SerializedName("strTweet2")
    private String strTweet2;
    @SerializedName("strTweet3")
    private String strTweet3;
    @SerializedName("strVideo")
    private String strVideo;
    @SerializedName("strPostponed")
    private String strPostponed;
    @SerializedName("strLocked")
    private String strLocked;


    public League(Integer idEvent, String idSoccerXML, Integer idAPIfootball, String strEvent, String strEventAlternate,
                  String strFilename, String strSport, String idLeague, String strLeague, String strSeason, String strDescriptionEN,
                  String strHomeTeam, String strAwayTeam, Integer intHomeScore, Integer intRound, Integer intAwayScore, Integer intSpectators,
                  String strHomeGoalDetails, String strHomeRedCards, String strHomeYellowCards, String trHomeLineupGoalkeeper,
                  String strHomeLineupDefense, String strHomeLineupMidfield, String strHomeLineupForward, String strHomeLineupSubstitutes,
                  String strHomeFormation, String strAwayRedCards, String strAwayYellowCards,
                  String strAwayGoalDetails, String strAwayLineupGoalkeeper, String strAwayLineupDefense, String strAwayLineupMidfield,
                  String strAwayLineupForward, String strAwayLineupSubstitutes, String strAwayFormation, Integer intHomeShots, Integer intAwayShots,
                  String dateEvent, String dateEventLocal, String strDate, String strTime, String strTimeLocal, String strTVStation, Integer idHomeTeam,
                  Integer idAwayTeam, String strResult, String strCircuit, String strCountry, String strCity, String strPoster, String strFanart,
                  String strThumb, String strBanner, String strMap, String strTweet1, String strTweet2, String strTweet3, String strVideo,
                  String strPostponed, String strLocked) {

        this.idEvent = idEvent;
//        this.idSoccerXML = idSoccerXML;
        this.idAPIfootball = idAPIfootball;
        this.strEvent = strEvent;
        this.strEventAlternate = strEventAlternate;
        this.strFilename = strFilename;
        this.strSport = strSport;
        this.idLeague = idLeague;
        this.strLeague = strLeague;
        this.strSeason = strSeason;
        this.strDescriptionEN = strDescriptionEN;
        this.strHomeTeam = strHomeTeam;
        this.strAwayTeam = strAwayTeam;
        this.intHomeScore = intHomeScore;
        this.intRound = intRound;
        this.intAwayScore = intAwayScore;
        this.intSpectators = intSpectators;
        this.strHomeGoalDetails = strHomeGoalDetails;
        this.strHomeRedCards = strHomeRedCards;
        this.strHomeYellowCards = strHomeYellowCards;
        this.trHomeLineupGoalkeeper = trHomeLineupGoalkeeper;
        this.strHomeLineupDefense = strHomeLineupDefense;
        this.strHomeLineupMidfield = strHomeLineupMidfield;
        this.strHomeLineupForward = strHomeLineupForward;
        this.strHomeLineupSubstitutes = strHomeLineupSubstitutes;
        this.strHomeFormation = strHomeFormation;
        this.strAwayRedCards = strAwayRedCards;
        this.strAwayYellowCards = strAwayYellowCards;
        this.strAwayGoalDetails = strAwayGoalDetails;
        this.strAwayLineupGoalkeeper = strAwayLineupGoalkeeper;
        this.strAwayLineupDefense = strAwayLineupDefense;
        this.strAwayLineupMidfield = strAwayLineupMidfield;
        this.strAwayLineupForward = strAwayLineupForward;
        this.strAwayLineupSubstitutes = strAwayLineupSubstitutes;
        this.strAwayFormation = strAwayFormation;
        this.intHomeShots = intHomeShots;
        this.intAwayShots = intAwayShots;
        this.dateEvent = dateEvent;
        this.dateEventLocal = dateEventLocal;
//        this.strDate = strDate;
        this.strTime = strTime;
        this.strTimeLocal = strTimeLocal;
        this.strTVStation = strTVStation;
        this.idHomeTeam = idHomeTeam;
        this.idAwayTeam = idAwayTeam;
        this.strResult = strResult;
        this.strCircuit = strCircuit;
        this.strCountry = strCountry;
        this.strCity = strCity;
        this.strPoster = strPoster;
        this.strFanart = strFanart;
        this.strThumb = strThumb;
        this.strBanner = strBanner;
        this.strMap = strMap;
        this.strTweet1 = strTweet1;
        this.strTweet2 = strTweet2;
        this.strTweet3 = strTweet3;
        this.strVideo = strVideo;
        this.strPostponed = strPostponed;
        this.strLocked = strLocked;
    }
    public League(){

    }

    protected League(Parcel in) {

        if (in.readByte() == 0) {
            idEvent = null;
        } else {
            idEvent = in.readInt();
        }
        idSoccerXML = in.readString();
        if (in.readByte() == 0) {
            idAPIfootball = null;
        } else {
            idAPIfootball = in.readInt();
        }
        strEvent = in.readString();
        strEventAlternate = in.readString();
        strFilename = in.readString();
        strSport = in.readString();
        idLeague = in.readString();
        strLeague = in.readString();
        strSeason = in.readString();
        strDescriptionEN = in.readString();
        strHomeTeam = in.readString();
        strAwayTeam = in.readString();
        if (in.readByte() == 0) {
            intHomeScore = null;
        } else {
            intHomeScore = in.readInt();
        }
        if (in.readByte() == 0) {
            intRound = null;
        } else {
            intRound = in.readInt();
        }
        if (in.readByte() == 0) {
            intAwayScore = null;
        } else {
            intAwayScore = in.readInt();
        }
        if (in.readByte() == 0) {
            intSpectators = null;
        } else {
            intSpectators = in.readInt();
        }
        strHomeGoalDetails = in.readString();
        strHomeRedCards = in.readString();
        strHomeYellowCards = in.readString();
        trHomeLineupGoalkeeper = in.readString();
        strHomeLineupDefense = in.readString();
        strHomeLineupMidfield = in.readString();
        strHomeLineupForward = in.readString();
        strHomeLineupSubstitutes = in.readString();
        strHomeFormation = in.readString();
        strAwayRedCards = in.readString();
        strAwayYellowCards = in.readString();
        strAwayGoalDetails = in.readString();
        strAwayLineupGoalkeeper = in.readString();
        strAwayLineupDefense = in.readString();
        strAwayLineupMidfield = in.readString();
        strAwayLineupForward = in.readString();
        strAwayLineupSubstitutes = in.readString();
        strAwayFormation = in.readString();
        if (in.readByte() == 0) {
            intHomeShots = null;
        } else {
            intHomeShots = in.readInt();
        }
        if (in.readByte() == 0) {
            intAwayShots = null;
        } else {
            intAwayShots = in.readInt();
        }
        dateEvent = in.readString();
        dateEventLocal = in.readString();
        strDate = in.readString();
        strTime = in.readString();
        strTimeLocal = in.readString();
        strTVStation = in.readString();
        if (in.readByte() == 0) {
            idHomeTeam = null;
        } else {
            idHomeTeam = in.readInt();
        }
        if (in.readByte() == 0) {
            idAwayTeam = null;
        } else {
            idAwayTeam = in.readInt();
        }
        strResult = in.readString();
        strCircuit = in.readString();
        strCountry = in.readString();
        strCity = in.readString();
        strPoster = in.readString();
        strFanart = in.readString();
        strThumb = in.readString();
        strBanner = in.readString();
        strMap = in.readString();
        strTweet1 = in.readString();
        strTweet2 = in.readString();
        strTweet3 = in.readString();
        strVideo = in.readString();
        strPostponed = in.readString();
        strLocked = in.readString();
    }

    public static final Creator<League> CREATOR = new Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        if (idEvent == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idEvent);
        }
//        parcel.writeString(idSoccerXML);
        if (idAPIfootball == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idAPIfootball);
        }
        parcel.writeString(strEvent);
        parcel.writeString(strEventAlternate);
        parcel.writeString(strFilename);
        parcel.writeString(strSport);
        parcel.writeString(idLeague);
        parcel.writeString(strLeague);
        parcel.writeString(strSeason);
        parcel.writeString(strDescriptionEN);
        parcel.writeString(strHomeTeam);
        parcel.writeString(strAwayTeam);
        if (intHomeScore == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intHomeScore);
        }
        if (intRound == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intRound);
        }
        if (intAwayScore == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intAwayScore);
        }
        if (intSpectators == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intSpectators);
        }
        parcel.writeString(strHomeGoalDetails);
        parcel.writeString(strHomeRedCards);
        parcel.writeString(strHomeYellowCards);
        parcel.writeString(trHomeLineupGoalkeeper);
        parcel.writeString(strHomeLineupDefense);
        parcel.writeString(strHomeLineupMidfield);
        parcel.writeString(strHomeLineupForward);
        parcel.writeString(strHomeLineupSubstitutes);
        parcel.writeString(strHomeFormation);
        parcel.writeString(strAwayRedCards);
        parcel.writeString(strAwayYellowCards);
        parcel.writeString(strAwayGoalDetails);
        parcel.writeString(strAwayLineupGoalkeeper);
        parcel.writeString(strAwayLineupDefense);
        parcel.writeString(strAwayLineupMidfield);
        parcel.writeString(strAwayLineupForward);
        parcel.writeString(strAwayLineupSubstitutes);
        parcel.writeString(strAwayFormation);
        if (intHomeShots == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intHomeShots);
        }
        if (intAwayShots == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(intAwayShots);
        }
        parcel.writeString(dateEvent);
        parcel.writeString(dateEventLocal);
        parcel.writeString(strDate);
        parcel.writeString(strTime);
        parcel.writeString(strTimeLocal);
        parcel.writeString(strTVStation);
        if (idHomeTeam == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idHomeTeam);
        }
        if (idAwayTeam == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idAwayTeam);
        }
        parcel.writeString(strResult);
        parcel.writeString(strCircuit);
        parcel.writeString(strCountry);
        parcel.writeString(strCity);
        parcel.writeString(strPoster);
        parcel.writeString(strFanart);
        parcel.writeString(strThumb);
        parcel.writeString(strBanner);
        parcel.writeString(strMap);
        parcel.writeString(strTweet1);
        parcel.writeString(strTweet2);
        parcel.writeString(strTweet3);
        parcel.writeString(strVideo);
        parcel.writeString(strPostponed);
        parcel.writeString(strLocked);
    }



    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getIdSoccerXML() {
        return idSoccerXML;
    }

    public void setIdSoccerXML(String idSoccerXML) {
        this.idSoccerXML = idSoccerXML;
    }

    public Integer getIdAPIfootball() {
        return idAPIfootball;
    }

    public void setIdAPIfootball(Integer idAPIfootball) {
        this.idAPIfootball = idAPIfootball;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public void setStrEvent(String strEvent) {
        this.strEvent = strEvent;
    }

    public String getStrEventAlternate() {
        return strEventAlternate;
    }

    public void setStrEventAlternate(String strEventAlternate) {
        this.strEventAlternate = strEventAlternate;
    }

    public String getStrFilename() {
        return strFilename;
    }

    public void setStrFilename(String strFilename) {
        this.strFilename = strFilename;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrSeason() {
        return strSeason;
    }

    public void setStrSeason(String strSeason) {
        this.strSeason = strSeason;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public void setStrHomeTeam(String strHomeTeam) {
        this.strHomeTeam = strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public void setStrAwayTeam(String strAwayTeam) {
        this.strAwayTeam = strAwayTeam;
    }

    public Integer getIntHomeScore() {
        return intHomeScore;
    }

    public void setIntHomeScore(Integer intHomeScore) {
        this.intHomeScore = intHomeScore;
    }

    public Integer getIntRound() {
        return intRound;
    }

    public void setIntRound(Integer intRound) {
        this.intRound = intRound;
    }

    public Integer getIntAwayScore() {
        return intAwayScore;
    }

    public void setIntAwayScore(Integer intAwayScore) {
        this.intAwayScore = intAwayScore;
    }

    public Integer getIntSpectators() {
        return intSpectators;
    }

    public void setIntSpectators(Integer intSpectators) {
        this.intSpectators = intSpectators;
    }

    public String getStrHomeGoalDetails() {
        return strHomeGoalDetails;
    }

    public void setStrHomeGoalDetails(String strHomeGoalDetails) {
        this.strHomeGoalDetails = strHomeGoalDetails;
    }

    public String getStrHomeRedCards() {
        return strHomeRedCards;
    }

    public void setStrHomeRedCards(String strHomeRedCards) {
        this.strHomeRedCards = strHomeRedCards;
    }

    public String getStrHomeYellowCards() {
        return strHomeYellowCards;
    }

    public void setStrHomeYellowCards(String strHomeYellowCards) {
        this.strHomeYellowCards = strHomeYellowCards;
    }

    public String getTrHomeLineupGoalkeeper() {
        return trHomeLineupGoalkeeper;
    }

    public void setTrHomeLineupGoalkeeper(String trHomeLineupGoalkeeper) {
        this.trHomeLineupGoalkeeper = trHomeLineupGoalkeeper;
    }

    public String getStrHomeLineupDefense() {
        return strHomeLineupDefense;
    }

    public void setStrHomeLineupDefense(String strHomeLineupDefense) {
        this.strHomeLineupDefense = strHomeLineupDefense;
    }

    public String getStrHomeLineupMidfield() {
        return strHomeLineupMidfield;
    }

    public void setStrHomeLineupMidfield(String strHomeLineupMidfield) {
        this.strHomeLineupMidfield = strHomeLineupMidfield;
    }

    public String getStrHomeLineupForward() {
        return strHomeLineupForward;
    }

    public void setStrHomeLineupForward(String strHomeLineupForward) {
        this.strHomeLineupForward = strHomeLineupForward;
    }

    public String getStrHomeLineupSubstitutes() {
        return strHomeLineupSubstitutes;
    }

    public void setStrHomeLineupSubstitutes(String strHomeLineupSubstitutes) {
        this.strHomeLineupSubstitutes = strHomeLineupSubstitutes;
    }

    public String getStrHomeFormation() {
        return strHomeFormation;
    }

    public void setStrHomeFormation(String strHomeFormation) {
        this.strHomeFormation = strHomeFormation;
    }

    public String getStrAwayRedCards() {
        return strAwayRedCards;
    }

    public void setStrAwayRedCards(String strAwayRedCards) {
        this.strAwayRedCards = strAwayRedCards;
    }

    public String getStrAwayYellowCards() {
        return strAwayYellowCards;
    }

    public void setStrAwayYellowCards(String strAwayYellowCards) {
        this.strAwayYellowCards = strAwayYellowCards;
    }

    public String getStrAwayGoalDetails() {
        return strAwayGoalDetails;
    }

    public void setStrAwayGoalDetails(String strAwayGoalDetails) {
        this.strAwayGoalDetails = strAwayGoalDetails;
    }

    public String getStrAwayLineupGoalkeeper() {
        return strAwayLineupGoalkeeper;
    }

    public void setStrAwayLineupGoalkeeper(String strAwayLineupGoalkeeper) {
        this.strAwayLineupGoalkeeper = strAwayLineupGoalkeeper;
    }

    public String getStrAwayLineupDefense() {
        return strAwayLineupDefense;
    }

    public void setStrAwayLineupDefense(String strAwayLineupDefense) {
        this.strAwayLineupDefense = strAwayLineupDefense;
    }

    public String getStrAwayLineupMidfield() {
        return strAwayLineupMidfield;
    }

    public void setStrAwayLineupMidfield(String strAwayLineupMidfield) {
        this.strAwayLineupMidfield = strAwayLineupMidfield;
    }

    public String getStrAwayLineupForward() {
        return strAwayLineupForward;
    }

    public void setStrAwayLineupForward(String strAwayLineupForward) {
        this.strAwayLineupForward = strAwayLineupForward;
    }

    public String getStrAwayLineupSubstitutes() {
        return strAwayLineupSubstitutes;
    }

    public void setStrAwayLineupSubstitutes(String strAwayLineupSubstitutes) {
        this.strAwayLineupSubstitutes = strAwayLineupSubstitutes;
    }

    public String getStrAwayFormation() {
        return strAwayFormation;
    }

    public void setStrAwayFormation(String strAwayFormation) {
        this.strAwayFormation = strAwayFormation;
    }

    public Integer getIntHomeShots() {
        return intHomeShots;
    }

    public void setIntHomeShots(Integer intHomeShots) {
        this.intHomeShots = intHomeShots;
    }

    public Integer getIntAwayShots() {
        return intAwayShots;
    }

    public void setIntAwayShots(Integer intAwayShots) {
        this.intAwayShots = intAwayShots;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getDateEventLocal() {
        return dateEventLocal;
    }

    public void setDateEventLocal(String dateEventLocal) {
        this.dateEventLocal = dateEventLocal;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrTimeLocal() {
        return strTimeLocal;
    }

    public void setStrTimeLocal(String strTimeLocal) {
        this.strTimeLocal = strTimeLocal;
    }

    public String getStrTVStation() {
        return strTVStation;
    }

    public void setStrTVStation(String strTVStation) {
        this.strTVStation = strTVStation;
    }

    public Integer getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(Integer idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public Integer getIdAwayTeam() {
        return idAwayTeam;
    }

    public void setIdAwayTeam(Integer idAwayTeam) {
        this.idAwayTeam = idAwayTeam;
    }

    public String getStrResult() {
        return strResult;
    }

    public void setStrResult(String strResult) {
        this.strResult = strResult;
    }

    public String getStrCircuit() {
        return strCircuit;
    }

    public void setStrCircuit(String strCircuit) {
        this.strCircuit = strCircuit;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrCity() {
        return strCity;
    }

    public void setStrCity(String strCity) {
        this.strCity = strCity;
    }

    public String getStrPoster() {
        return strPoster;
    }

    public void setStrPoster(String strPoster) {
        this.strPoster = strPoster;
    }

    public String getStrFanart() {
        return strFanart;
    }

    public void setStrFanart(String strFanart) {
        this.strFanart = strFanart;
    }

    public String getStrThumb() {
        return strThumb;
    }

    public void setStrThumb(String strThumb) {
        this.strThumb = strThumb;
    }

    public String getStrBanner() {
        return strBanner;
    }

    public void setStrBanner(String strBanner) {
        this.strBanner = strBanner;
    }

    public String getStrMap() {
        return strMap;
    }

    public void setStrMap(String strMap) {
        this.strMap = strMap;
    }

    public String getStrTweet1() {
        return strTweet1;
    }

    public void setStrTweet1(String strTweet1) {
        this.strTweet1 = strTweet1;
    }

    public String getStrTweet2() {
        return strTweet2;
    }

    public void setStrTweet2(String strTweet2) {
        this.strTweet2 = strTweet2;
    }

    public String getStrTweet3() {
        return strTweet3;
    }

    public void setStrTweet3(String strTweet3) {
        this.strTweet3 = strTweet3;
    }

    public String getStrVideo() {
        return strVideo;
    }

    public void setStrVideo(String strVideo) {
        this.strVideo = strVideo;
    }

    public String getStrPostponed() {
        return strPostponed;
    }

    public void setStrPostponed(String strPostponed) {
        this.strPostponed = strPostponed;
    }

    public String getStrLocked() {
        return strLocked;
    }

    public void setStrLocked(String strLocked) {
        this.strLocked = strLocked;
    }
}
