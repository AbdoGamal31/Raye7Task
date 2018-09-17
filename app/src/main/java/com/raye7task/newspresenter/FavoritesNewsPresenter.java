package com.raye7task.newspresenter;

import com.raye7task.newusecase.GetFavouritesNews;
import com.raye7task.roomdatabase.FavouritesNewsEntity;

import java.util.List;

public class FavoritesNewsPresenter implements IFavoritesNewsPresenter {
    private GetFavouritesNews favouritesNews;

    public FavoritesNewsPresenter() {
        this.favouritesNews = new GetFavouritesNews();
    }

    public List<FavouritesNewsEntity> getNewsFromDatabase() {
        return favouritesNews.getAllFavouriteNews();
    }
}
