package com.raye7task.newsapi;

import com.raye7task.newsmodel.NewsObject;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsApis {
    @GET("v2/top-headlines?country=us&apiKey=2b77b0c7a6ea4e5daca90337c443422f")
    Observable<NewsObject> getAllNews();
}
