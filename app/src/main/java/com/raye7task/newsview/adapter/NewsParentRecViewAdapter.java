package com.raye7task.newsview.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raye7task.R;
import com.raye7task.newsmodel.NewsGroupedByDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsParentRecViewAdapter extends RecyclerView.Adapter<NewsParentRecViewAdapter.ParentViewHolder> {
    private NewsChildRecViewDataAdapter childRecViewDataAdapter;
    private List<NewsGroupedByDay> newsGroupedByDayList;
    private Context context;

    public NewsParentRecViewAdapter() {
        newsGroupedByDayList = new ArrayList<>();
        childRecViewDataAdapter = new NewsChildRecViewDataAdapter();
    }

    @Override
    public ParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.news_rec_view, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentViewHolder holder, int position) {
        NewsGroupedByDay newsDate = newsGroupedByDayList.get(position);
        holder.dateTxt.setText(newsDate.getDay());


        childRecViewDataAdapter.setArticleList(newsDate.getArticleList());
        holder.newsRecViewData.setLayoutManager(new LinearLayoutManager(context));
        holder.newsRecViewData.setAdapter(childRecViewDataAdapter);

    }

    public void setNewsGroupedByDayList(List<NewsGroupedByDay> newsList) {
        if (newsGroupedByDayList == null) {
            return;
        }
        newsGroupedByDayList.clear();
        newsGroupedByDayList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsGroupedByDayList.size();
    }

    protected class ParentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date_txt)
        TextView dateTxt;
        @BindView(R.id.news_rec_view_data)
        RecyclerView newsRecViewData;

        private ParentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public NewsChildRecViewDataAdapter getChildRecViewDataAdapter() {
        return childRecViewDataAdapter;
    }
}
