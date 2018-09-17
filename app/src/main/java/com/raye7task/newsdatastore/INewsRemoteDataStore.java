package com.raye7task.newsdatastore;

import com.raye7task.newsmodel.NewsObject;

import io.reactivex.Observable;

public interface INewsRemoteDataStore {
    Observable<NewsObject> getAllNews();
}
