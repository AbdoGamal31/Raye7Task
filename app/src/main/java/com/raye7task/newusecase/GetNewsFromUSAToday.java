package com.raye7task.newusecase;

import com.raye7task.newsdatastore.NewsRemoteDataStore;
import com.raye7task.newsmodel.NewsGroupedByDay;
import com.raye7task.newsmodel.NewsObject;
import com.raye7task.newsrepository.INewsRepository;
import com.raye7task.newsrepository.NewsRepository;
import com.raye7task.utility.TimeUtilities;

import java.util.List;

import io.reactivex.Observable;

public class GetNewsFromUSAToday {
    INewsRepository iNewsRepository;
    String USATodayID = "USA Today";

    public GetNewsFromUSAToday() {
        this.iNewsRepository = new NewsRepository(new NewsRemoteDataStore());
    }

    private Observable<NewsObject> getAllNewsFromAllSources() {
        return iNewsRepository.getAllNews();
    }

    public Observable<List<NewsGroupedByDay>> getAllNewsFromUSATodayGroupedByDay() {
        return getAllNewsFromAllSources()

                .flatMap(newsObject -> Observable.fromIterable(newsObject.getArticles()))
                .filter(article -> article.getSource().getName().equals(USATodayID))
                .groupBy(USATodayArticle ->
                        TimeUtilities.getFormattedDay(USATodayArticle.getPublishedAt(),
                                TimeUtilities.dateFullFormat,
                                TimeUtilities.dateFormat))
                .flatMap(articleGroupedByDay -> {
                    String articleGroupedByDayKey = articleGroupedByDay.getKey();
                    return Observable.zip(
                            Observable.just(articleGroupedByDayKey),
                            articleGroupedByDay.toList().toObservable(),
                            NewsGroupedByDay::new);
                })
                .toList()
                .toObservable();
    }
}
