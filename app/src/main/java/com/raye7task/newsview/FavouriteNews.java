package com.raye7task.newsview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raye7task.R;
import com.raye7task.newsmodel.Article;
import com.raye7task.newspresenter.FavoritesNewsPresenter;
import com.raye7task.newspresenter.IFavoritesNewsPresenter;
import com.raye7task.newsview.adapter.NewsChildRecViewDataAdapter;
import com.raye7task.newsviewmodel.FavoritesNewViewModel;
import com.raye7task.roomdatabase.FavouritesNewsDAO;
import com.raye7task.roomdatabase.FavouritesNewsEntity;
import com.raye7task.roomdatabase.NewsDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;


public class FavouriteNews extends Fragment implements IAllNews {
    @BindView(R.id.date_recycler_view)
    RecyclerView dateRecyclerView;
    Unbinder unbinder;
    IFavoritesNewsPresenter iFavoritesNewsPresenter;
    private NewsChildRecViewDataAdapter newsChildRecViewDataAdapter;
    private FavoritesNewViewModel favoritesNewViewModel;
    List<FavouritesNewsEntity> favouritesNewsEntityList;

    public FavouriteNews() {
        newsChildRecViewDataAdapter = new NewsChildRecViewDataAdapter();
        iFavoritesNewsPresenter = new FavoritesNewsPresenter();
        newsChildRecViewDataAdapter.setFavorites(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        favoritesNewViewModel = ViewModelProviders.of(this).get(FavoritesNewViewModel.class);
        if (favoritesNewViewModel.getArticleList() != null) {
            displayFavoritesNews(favoritesNewViewModel.getArticleList());
        }
        newsChildRecViewDataAdapter
                .getNewsURLLink()
                .subscribe(linkURL -> openExternalWebView(linkURL, getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouritesNewsEntityList = iFavoritesNewsPresenter.getNewsFromDatabase();

        displayFavoritesNews(mapFavoritesNewsEntityToArticle(
                favouritesNewsEntityList).blockingFirst());
        newsChildRecViewDataAdapter.getFavoriteNewsObservable()
                .subscribe(articleBooleanPair -> removeFromFavoritesNews(articleBooleanPair.first));

    }

    private void removeFromFavoritesNews(Article article) {
        FavouritesNewsDAO appDatabase = NewsDatabase.getAppDatabaseBuilderInstance(getContext()).favouritesNewsDAO();
        appDatabase.deleteNewsByID(article.getUrl());
        newsChildRecViewDataAdapter.notifyDataSetChanged();
    }

    private Observable<List<Article>> mapFavoritesNewsEntityToArticle(List<FavouritesNewsEntity> favouritesNewsEntityList) {
        return io.reactivex.Observable.fromIterable(favouritesNewsEntityList).map(favouritesNewsEntity -> {
            Article article = new Article();
            article.setTitle(favouritesNewsEntity.getNewsName());
            article.setPublishedAt(favouritesNewsEntity.getNewsTime());
            article.setUrlToImage(favouritesNewsEntity.getNewsImageURL());
            article.setUrl(favouritesNewsEntity.getNewsURL());
            return article;
        }).toList().toObservable();
    }

    public void displayFavoritesNews(List<Article> articleList) {
        newsChildRecViewDataAdapter.setArticleList(articleList);
        favoritesNewViewModel.setArticleList(articleList);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dateRecyclerView.setAdapter(newsChildRecViewDataAdapter);
        newsChildRecViewDataAdapter.notifyDataSetChanged();
    }

}
