package com.raye7task.newspresenter;

import com.raye7task.R;
import com.raye7task.newsmodel.Article;
import com.raye7task.newsview.IFavoritesNews;
import com.raye7task.newusecase.GetFavouritesNews;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoritesNewsPresenter implements IFavoritesNewsPresenter {
    private GetFavouritesNews favouritesNews;
    private IFavoritesNews favoritesView;

    public FavoritesNewsPresenter(IFavoritesNews favoritesView) {
        this.favoritesView = favoritesView;
        this.favouritesNews = new GetFavouritesNews();
    }

    public void getNewsFromDatabase() {
        favouritesNews.mapFavoritesNewsEntityToArticle()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(List<Article> articleList) throws Exception {
                        if (articleList.size() <= 0) {
                            String errorMessage = "No current favorites news ...";
                            int errorIcon = R.drawable.no_news;
                            favoritesView.showErrorOverlay(errorMessage, errorIcon, false);
                        } else {
                            favoritesView.displayFavoritesNews(articleList);
                        }
                    }
                });

    }
}
