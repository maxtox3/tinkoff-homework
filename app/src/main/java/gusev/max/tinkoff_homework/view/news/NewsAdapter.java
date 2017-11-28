package gusev.max.tinkoff_homework.view.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.businesslogic.model.News;

/**
 * Created by v on 25/11/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<News> newsList;

    public NewsAdapter() {
       this.newsList = new ArrayList<>();
    }

    public void setNews(List<News> news) {
        this.newsList = news;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }
}
