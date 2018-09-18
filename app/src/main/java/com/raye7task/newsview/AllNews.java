package com.raye7task.newsview;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.raye7task.newsmodel.NewsGroupedByDay;
import com.raye7task.newspresenter.AllNewsPresenter;
import com.raye7task.newspresenter.INewsPresenter;
import com.raye7task.newsview.adapter.NewsParentRecViewAdapter;
import com.raye7task.newsviewmodel.AllNewsViewModel;
import com.raye7task.roomdatabase.FavouritesNewsDAO;
import com.raye7task.roomdatabase.FavouritesNewsEntity;
import com.raye7task.roomdatabase.NewsDatabase;
import com.raye7task.utility.TimeUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllNews extends Fragment implements IAllNews {
    @BindView(R.id.date_recycler_view)
    RecyclerView dateRecyclerView;
    Unbinder unbinder;
    private NewsParentRecViewAdapter newsParentRecViewAdapter;
    private ProgressDialog loadingInductor;
    private INewsPresenter iNewsPresenter;
    private AllNewsViewModel allNewsViewModel;

    public AllNews() {
        newsParentRecViewAdapter = new NewsParentRecViewAdapter();
        iNewsPresenter = new AllNewsPresenter(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingInductor = new ProgressDialog(getContext());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);
        unbinder = ButterKnife.bind(this, view);

        allNewsViewModel = ViewModelProviders.of(this).get(AllNewsViewModel.class);
        newsParentRecViewAdapter.getChildRecViewDataAdapter()
                .getNewsURLLink()
                .subscribe(linkURL -> openExternalWebView(linkURL, getContext()));
        newsParentRecViewAdapter.getChildRecViewDataAdapter()
                .getFavoriteNewsObservable()
                .subscribe(articleBooleanPair -> mapArticleToDataBaseEntity(articleBooleanPair.first,
                        articleBooleanPair.second));
        if (allNewsViewModel.getNewsGroupedByDay() != null) {
            displayNewsGroupedByDay(allNewsViewModel.getNewsGroupedByDay());
        }
        return view;
    }

    private void mapArticleToDataBaseEntity(Article article, boolean isChecked) {
        FavouritesNewsDAO appDatabase = NewsDatabase.getAppDatabaseBuilderInstance(getContext()).favouritesNewsDAO();
        String newsName = article.getTitle();
        String newsTime = TimeUtilities.getFormattedDay("2018-09-13T19:56:52Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        String newsImageURL = article.getUrlToImage();
        String newsURL = article.getUrl();
        FavouritesNewsEntity favouritesNewsEntity = new FavouritesNewsEntity(newsName, newsTime, newsImageURL, newsURL);
        if (isChecked) {
            appDatabase.insertPlayer(favouritesNewsEntity);
        } else {
            appDatabase.deleteNewsByID(favouritesNewsEntity.getNewsURL());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iNewsPresenter.getAllNewsFromUSATodayGroupedByDay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoadingIndicator() {
        loadingInductor.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingInductor.setCancelable(false);
        loadingInductor.show();
    }

    @Override
    public void hideLoadingIndicator() {
        if (loadingInductor.isShowing())
            loadingInductor.hide();
    }

    @Override
    public void displayNewsGroupedByDay(List<NewsGroupedByDay> newsGroupedByDayList) {
        newsParentRecViewAdapter.setNewsGroupedByDayList(newsGroupedByDayList);
        allNewsViewModel.setNewsGroupedByDay(newsGroupedByDayList);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dateRecyclerView.setAdapter(newsParentRecViewAdapter);
        newsParentRecViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorOverlay(String errorMessage, int errorIcon, boolean b) {
        Dialog dialog = buildShowErrorOverlay(getContext(), errorMessage, errorIcon);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_overlay_primary);
        if (!b) {
            dialogButton.setText(R.string.okay);
            dialogButton.setOnClickListener(v -> {
                dialog.dismiss();
            });
        } else {
            dialogButton.setOnClickListener(v -> {
                dialog.dismiss();
                iNewsPresenter.getAllNewsFromUSATodayGroupedByDay();
            });
        }
        dialog.show();
    }
}
