package com.example.wdsportz.viewmodels;

/**
 * Created by khrishawn
 */
public class VideoViewModel {
    String title;
    String videoimageURL;



    public VideoViewModel() {

    }

    public VideoViewModel(String title, String videoimageURL) {
        this.title = title;
        this.videoimageURL = videoimageURL;
    }

    public String getVideoimageURL() {
        return videoimageURL;
    }

    public void setVideoimageURL(String videoimageURL) {this.videoimageURL = videoimageURL; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
