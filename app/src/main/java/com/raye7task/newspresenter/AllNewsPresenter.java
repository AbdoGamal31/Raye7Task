package com.raye7task.newspresenter;

import com.raye7task.MyApplication;
import com.raye7task.R;
import com.raye7task.network.NetworkError;
import com.raye7task.newsview.IAllNews;
import com.raye7task.newusecase.GetNewsFromUSAToday;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllNewsPresenter implements INewsPresenter {
    private GetNewsFromUSAToday newsFromUSAToday;
    private IAllNews iAllNews;

    public AllNewsPresenter(IAllNews iAllNews) {
        this.newsFromUSAToday = new GetNewsFromUSAToday();
        this.iAllNews = iAllNews;
    }

    @Override
    public void getAllNewsFromUSATodayGroupedByDay() {
        iAllNews.showLoadingIndicator();
        newsFromUSAToday.getAllNewsFromUSATodayGroupedByDay()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(newsGroupedByDayList -> {
                    iAllNews.displayNewsGroupedByDay(newsGroupedByDayList);
                    iAllNews.hideLoadingIndicator();
                }, throwable ->
                {

                    if (throwable instanceof NetworkError) {
                        String errorMessage = "No internet connection, check your connection and try again later ...";
                        int errorIcon = R.drawable.no_internet_connection;
                        iAllNews.showErrorOverlay(errorMessage, errorIcon, true);
                    } else {
                        String errorMessage = "No recent news ...";
                        int errorIcon = R.drawable.no_news;
                        iAllNews.showErrorOverlay(errorMessage, errorIcon,false);
                    }
                    iAllNews.hideLoadingIndicator();

                });
    }

}
