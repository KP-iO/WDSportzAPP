package com.example.wdsportz.ViewModels;

public class NewsFeedViewModel {
    String title;
    String newsImageURL;
    String newsDesc;
    String date;
    String chatbox_id;



    public NewsFeedViewModel() {

    }

    public NewsFeedViewModel(String title, String newsImageURL, String newsDesc, String date, String chatbox_id) {
        this.title = title;
        this.newsImageURL = newsImageURL;
        this.newsDesc = newsDesc;
        this.date = date;
        this.chatbox_id =chatbox_id;

    }

    public  String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {this.newsDesc = newsDesc; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {this.date = date; }

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

    public String getChatbox_id() {
        return chatbox_id;
    }

    public void setChatbox_id(String chatbox_id) {
        this.chatbox_id = chatbox_id;
    }
}

