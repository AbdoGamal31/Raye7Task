package com.raye7task.newsdatastore;

import com.raye7task.roomdatabase.FavouritesNewsDAO;
import com.raye7task.roomdatabase.FavouritesNewsEntity;
import com.raye7task.roomdatabase.NewsDatabase;

import java.util.List;

import static com.raye7task.MyApplication.getContext;

public class NewsLocalDataStore implements INewsLocalDataStore {
    @Override
    public List<FavouritesNewsEntity> getAllFavouriteNews() {
        FavouritesNewsDAO appDatabaseDao = NewsDatabase.getAppDatabaseBuilderInstance(getContext()).favouritesNewsDAO();
        return appDatabaseDao.getAllFavouriteNews();
    }
}
