package com.raye7task.roomdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavouritesNewsDAO {
    @Query("select * from favNews")
    List<FavouritesNewsEntity> getAllFavouriteNews();

    @Insert
    void insertPlayer(FavouritesNewsEntity favouritesNewsEntity);

    @Query("delete  from favNews where news_url  = :newURL")
    void deleteNewsByID(String newURL);
}
