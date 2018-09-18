package com.raye7task.newsview;

import android.app.Dialog;
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
import android.widget.Button;

import com.raye7task.R;
import com.raye7task.newsmodel.Article;
import com.raye7task.newspresenter.FavoritesNewsPresenter;
import com.raye7task.newspresenter.IFavoritesNewsPresenter;
import com.raye7task.newsview.adapter.NewsChildRecViewDataAdapter;
import com.raye7task.newsviewmodel.FavoritesNewViewModel;
import com.raye7task.roomdatabase.FavouritesNewsDAO;
import com.raye7task.roomdatabase.NewsDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FavouriteNews extends Fragment implements IFavoritesNews {

    IFavoritesNewsPresenter iFavoritesNewsPresenter;
    @BindView(R.id.date_recycler_view)
    RecyclerView dateRecyclerView;
    Unbinder unbinder;
    private NewsChildRecViewDataAdapter newsChildRecViewDataAdapter;
    private FavoritesNewViewModel favoritesNewViewModel;

    public FavouriteNews() {
        newsChildRecViewDataAdapter = new NewsChildRecViewDataAdapter();
        iFavoritesNewsPresenter = new FavoritesNewsPresenter(this);
        newsChildRecViewDataAdapter.setFavorites(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritesNewViewModel = ViewModelProviders.of(this).get(FavoritesNewViewModel.class);
        iFavoritesNewsPresenter.getNewsFromDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_news, container, false);
        if (favoritesNewViewModel.getArticleList() != null) {
            displayFavoritesNews(favoritesNewViewModel.getArticleList());
        }
        newsChildRecViewDataAdapter
                .getNewsURLLink()
                .subscribe(linkURL -> openExternalWebView(linkURL, getContext()));
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsChildRecViewDataAdapter.getFavoriteNewsObservable()
                .subscribe(articleBooleanPair -> removeFromFavoritesNews(articleBooleanPair.first));

    }

    private void removeFromFavoritesNews(Article article) {
        FavouritesNewsDAO appDatabase = NewsDatabase.getAppDatabaseBuilderInstance(getContext()).favouritesNewsDAO();
        appDatabase.deleteNewsByID(article.getUrl());
        newsChildRecViewDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFavoritesNews(List<Article> articleList) {
        newsChildRecViewDataAdapter.setArticleList(articleList);
        favoritesNewViewModel.setArticleList(articleList);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dateRecyclerView.setAdapter(newsChildRecViewDataAdapter);
        newsChildRecViewDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorOverlay(String errorMessage, int errorIcon, boolean b) {
        Dialog dialog = buildShowErrorOverlay(getContext(), errorMessage, errorIcon);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_overlay_primary);
        dialogButton.setText(R.string.okay);
        dialogButton.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
