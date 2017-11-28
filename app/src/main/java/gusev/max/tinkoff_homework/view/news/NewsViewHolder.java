package gusev.max.tinkoff_homework.view.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.businesslogic.model.News;

/**
 * Created by v on 25/11/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.news_body_text_view)
    TextView body;
    @BindView(R.id.date_text_view)
    TextView date;

    NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(News news) {
        body.setText(news.getText());
        date.setText(news.getPrettyPublicationDate());
    }
}
