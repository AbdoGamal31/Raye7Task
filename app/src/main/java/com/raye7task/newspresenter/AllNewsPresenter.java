package com.raye7task.newspresenter;

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
                    iAllNews.hideLoadingIndicator();
                });
    }
}
