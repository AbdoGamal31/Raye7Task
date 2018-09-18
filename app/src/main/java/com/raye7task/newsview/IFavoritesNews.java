package com.raye7task.newsview;

import com.raye7task.newsmodel.Article;

import java.util.List;

public interface IFavoritesNews extends IAllNews {
    void displayFavoritesNews(List<Article> articleList);

}
