package com.raye7task.newsrepository;

import com.raye7task.newsdatastore.INewsLocalDataStore;
import com.raye7task.newsdatastore.INewsRemoteDataStore;
import com.raye7task.newsmodel.NewsObject;
import com.raye7task.roomdatabase.FavouritesNewsEntity;

import java.util.List;

import io.reactivex.Observable;

public class NewsRepository implements INewsRepository, INewsLocalDataStore {
    INewsRemoteDataStore iNewsRemoteDataStore;
    INewsLocalDataStore iNewsLocalDataStore;

    public NewsRepository(INewsRemoteDataStore iNewsRemoteDataStore) {
        this.iNewsRemoteDataStore = iNewsRemoteDataStore;
    }

    public NewsRepository(INewsLocalDataStore iNewsLocalDataStore) {
        this.iNewsLocalDataStore = iNewsLocalDataStore;
    }

    @Override
    public Observable<NewsObject> getAllNews() {
        return iNewsRemoteDataStore.getAllNews();
    }

    @Override
    public List<FavouritesNewsEntity> getAllFavouriteNews() {
        return iNewsLocalDataStore.getAllFavouriteNews();
    }
}
