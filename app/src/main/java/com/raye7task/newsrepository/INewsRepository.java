package com.raye7task.newsrepository;

import com.raye7task.newsmodel.NewsObject;

import io.reactivex.Observable;

public interface INewsRepository {
    Observable<NewsObject> getAllNews();
}
