package com.raye7task.newsview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.raye7task.newsmodel.NewsGroupedByDay;

import java.util.List;

public interface IAllNews {

    default void showLoadingIndicator() {
    }

    default void hideLoadingIndicator() {
    }

    default void openExternalWebView(String URL, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        context.startActivity(browserIntent);
    }

    default void displayNewsGroupedByDay(List<NewsGroupedByDay> newsGroupedByDayList) {
    }
}
