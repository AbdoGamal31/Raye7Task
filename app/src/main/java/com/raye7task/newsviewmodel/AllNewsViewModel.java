package com.raye7task.newsviewmodel;

import android.arch.lifecycle.ViewModel;

import com.raye7task.newsmodel.NewsGroupedByDay;

import java.util.List;

public class AllNewsViewModel extends ViewModel {
    List<NewsGroupedByDay> newsGroupedByDayList ;

    public List<NewsGroupedByDay> getNewsGroupedByDay() {
        return newsGroupedByDayList;
    }

    public void setNewsGroupedByDay(List<NewsGroupedByDay> newsGroupedByDayList) {
        this.newsGroupedByDayList = newsGroupedByDayList;
    }
}
