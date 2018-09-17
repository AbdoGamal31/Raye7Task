package com.raye7task.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.raye7task.newsmodel.Article;

import java.util.List;

@Entity(tableName = "favNews")
public class FavouritesNewsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_ID")
    private int newsID;
    @ColumnInfo(name = "news_name")
    private String newsName;
    @ColumnInfo(name = "news_time")
    private String newsTime;
    @ColumnInfo(name = "news_image_url")
    private String newsImageURL;
    @ColumnInfo(name = "news_url")
    private String newsURL;


    public FavouritesNewsEntity(String newsName, String newsTime, String newsImageURL, String newsURL) {
        this.newsName = newsName;
        this.newsTime = newsTime;
        this.newsImageURL = newsImageURL;
        this.newsURL = newsURL;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}
