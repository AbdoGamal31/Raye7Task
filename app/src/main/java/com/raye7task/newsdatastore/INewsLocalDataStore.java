package com.raye7task.newsdatastore;

import com.raye7task.roomdatabase.FavouritesNewsEntity;

import java.util.List;

public interface INewsLocalDataStore {
    List<FavouritesNewsEntity> getAllFavouriteNews();
}
