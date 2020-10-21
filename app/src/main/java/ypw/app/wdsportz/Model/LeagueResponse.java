package ypw.app.wdsportz.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khrishawn
 */


public class LeagueResponse implements Parcelable {

    @SerializedName("events")
    private List<League> results;


    protected LeagueResponse(Parcel in) {
        results = in.createTypedArrayList(League.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeagueResponse> CREATOR = new Creator<LeagueResponse>() {
        @Override
        public LeagueResponse createFromParcel(Parcel in) {
            return new LeagueResponse(in);
        }

        @Override
        public LeagueResponse[] newArray(int size) {
            return new LeagueResponse[size];
        }
    };

    public List<League> getMatches() {
        return results;
    }

    public void setResults(List<League> results) {
        this.results = results;
    }
}

/*
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<League> getResults() {
        return results;
    }



    public void setMatches(List<League> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
//        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    public LeagueResponse() {
    }

    protected LeagueResponse(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(League.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<LeagueResponse> CREATOR = new Parcelable.Creator<LeagueResponse>() {
        @Override
        public LeagueResponse createFromParcel(Parcel source) {
            return new LeagueResponse(source);
        }

        @Override
        public LeagueResponse[] newArray(int size) {
            return new LeagueResponse[size];
        }
    };
}*/
