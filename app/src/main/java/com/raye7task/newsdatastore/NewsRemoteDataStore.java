package com.raye7task.newsdatastore;

import com.raye7task.network.component.NetworkComponent;
import com.raye7task.newsapi.NewsApis;
import com.raye7task.newsmodel.NewsObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class NewsRemoteDataStore implements INewsRemoteDataStore {
    @Inject
    Retrofit retrofit;
    NewsApis newsApis;

    public NewsRemoteDataStore() {
        NetworkComponent.Initializer.buildComponent().inject(this);
        newsApis = retrofit.create(NewsApis.class);
    }

    @Override
    public Observable<NewsObject> getAllNews() {
        return newsApis.getAllNews();
    }
}
