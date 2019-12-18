package com.example.wdsportz.ViewModels;

public class NewsFeedViewModel {
    String title;
    String newsImageURL;
    String newsDesc;



    public NewsFeedViewModel() {

    }

    public NewsFeedViewModel(String title, String newsImageURL, String newsDesc) {
        this.title = title;
        this.newsImageURL = newsImageURL;
        this.newsDesc = newsDesc;
    }




    public  String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {this.newsDesc = newsDesc; }



    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {this.newsImageURL = newsImageURL; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

