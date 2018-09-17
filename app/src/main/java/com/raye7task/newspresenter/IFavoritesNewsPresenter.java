package com.raye7task.newspresenter;

import com.raye7task.roomdatabase.FavouritesNewsEntity;

import java.util.List;

public interface IFavoritesNewsPresenter {
    List<FavouritesNewsEntity> getNewsFromDatabase();
}
