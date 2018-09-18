package com.raye7task.newusecase;

import com.raye7task.newsdatastore.NewsLocalDataStore;
import com.raye7task.newsmodel.Article;
import com.raye7task.newsrepository.INewsRepository;
import com.raye7task.newsrepository.NewsRepository;
import com.raye7task.roomdatabase.FavouritesNewsDAO;
import com.raye7task.roomdatabase.FavouritesNewsEntity;
import com.raye7task.roomdatabase.NewsDatabase;

import java.util.List;

import io.reactivex.Observable;

import static com.raye7task.MyApplication.getContext;

public class GetFavouritesNews {
    INewsRepository iNewsRepository;


    public GetFavouritesNews() {
        this.iNewsRepository = new NewsRepository(new NewsLocalDataStore());
    }

    private List<FavouritesNewsEntity> getAllFavouriteNews() {
        FavouritesNewsDAO appDatabaseDao = NewsDatabase.getAppDatabaseBuilderInstance(getContext()).favouritesNewsDAO();
        return appDatabaseDao.getAllFavouriteNews();
    }

    public Observable<List<Article>> mapFavoritesNewsEntityToArticle() {
        return io.reactivex.Observable.fromIterable(getAllFavouriteNews()).map(favouritesNewsEntity -> {
            Article article = new Article();
            article.setTitle(favouritesNewsEntity.getNewsName());
            article.setPublishedAt(favouritesNewsEntity.getNewsTime());
            article.setUrlToImage(favouritesNewsEntity.getNewsImageURL());
            article.setUrl(favouritesNewsEntity.getNewsURL());
            return article;
        }).toList().toObservable();
    }

}
