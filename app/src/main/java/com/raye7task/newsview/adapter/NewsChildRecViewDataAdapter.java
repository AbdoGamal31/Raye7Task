package com.raye7task.newsview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.raye7task.R;
import com.raye7task.newsmodel.Article;
import com.raye7task.utility.TimeUtilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class NewsChildRecViewDataAdapter extends RecyclerView.Adapter<NewsChildRecViewDataAdapter.ChildViewHolder> {
    private List<Article> articles;
    private Context context;
    private Subject<String> newsURLLink = PublishSubject.create();
    private Subject<Pair<Article, Boolean>> favoriteNewsObservable = PublishSubject.create();
    private boolean isFavorites = false;

    public NewsChildRecViewDataAdapter() {
        articles = new ArrayList<>();
    }

    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.news_item, parent, false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChildViewHolder holder, final int position) {
        Article article = articles.get(position);
        holder.newsName.setText(article.getTitle());
        String time = TimeUtilities.getFormattedDay("2018-09-13T19:56:52Z",
                TimeUtilities.dateFullFormat,
                TimeUtilities.timeHoursSeconds);
        holder.newsTime.setText(time);
        if (article.getUrlToImage() != null) {
            Picasso.get()
                    .load(article.getUrlToImage())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.aa);
        }
        setIsFavoriteNews(isFavorites, holder.toggleButton);
        holder.toggleButton.setOnClickListener(view -> {
            boolean isChecked = holder.toggleButton.isChecked();
            if (!isFavorites) {
                makeNewsIsFavorite(isChecked, holder.toggleButton);
            } else {
                removeFromFavoritesNews(holder.toggleButton, position);

            }

            Pair<Article, Boolean> pair = new Pair<>(article, isChecked);
            favoriteNewsObservable.onNext(pair);
        });

    }

    private void removeFromFavoritesNews(ToggleButton toggleButton, int position) {
        setFavoriteNewsBackGround(R.drawable.star_gray, toggleButton);
        articles.remove(position);
        notifyDataSetChanged();
    }

    private void makeNewsIsFavorite(boolean isChecked, ToggleButton toggleButton) {
        if (isChecked) {
            setFavoriteNewsBackGround(R.drawable.star_gold, toggleButton);
        } else {
            setFavoriteNewsBackGround(R.drawable.star_gray, toggleButton);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public Subject<String> getNewsURLLink() {
        return newsURLLink;
    }

    protected class ChildViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_name)
        TextView newsName;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.news_time)
        TextView newsTime;
        @BindView(R.id.toggleButton)
        ToggleButton toggleButton;

        private ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(view1 -> {
                Article article = articles.get(getAdapterPosition());
                newsURLLink.onNext(article.getUrl());
            });
        }
    }

    public void setArticleList(List<Article> articleList) {
        if (articles == null) {
            return;
        }
        articles.clear();
        articles.addAll(articleList);
        notifyDataSetChanged();
    }

    public Subject<Pair<Article, Boolean>> getFavoriteNewsObservable() {
        return favoriteNewsObservable;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }

    private void setFavoriteNewsBackGround(int backGround, ToggleButton favoriteBar) {
        favoriteBar.setBackgroundDrawable(ContextCompat.
                getDrawable(context, backGround));
    }

    private void setIsFavoriteNews(boolean isFavorites, ToggleButton toggleButton) {
        if (isFavorites) {
            setFavoriteNewsBackGround(R.drawable.star_gold, toggleButton);
        } else {
            setFavoriteNewsBackGround(R.drawable.star_gray, toggleButton);
        }
    }
}