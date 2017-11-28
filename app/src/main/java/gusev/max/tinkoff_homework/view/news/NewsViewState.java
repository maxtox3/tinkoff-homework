package gusev.max.tinkoff_homework.view.news;


import java.util.List;

import gusev.max.tinkoff_homework.businesslogic.model.News;

/**
 * Created by v on 25/11/2017.
 */

public interface NewsViewState {

    /**
     * Indicates that the news is loading
     */
    final class NewsLoading implements NewsViewState {}

    /**
     * Indicates that an error has occurred while loading the news
     */
    final class NewsLoadingError implements NewsViewState {
        private final Throwable error;

        public NewsLoadingError(Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }
    }

    /**
     * Indicates that the news data has been loaded successfully
     */
    final class NewsLoaded implements NewsViewState {
        private final List<News> data;

        public NewsLoaded(List<News> data) {
            this.data = data;
        }

        public List<News> getData() {
            return data;
        }
    }

    /**
     * Indicates that loading the newest items via pull to refresh has started
     */
    final class PullToRefreshLoading implements NewsViewState {
    }

    /**
     * Indicates that an error while loading the newest items via pull to refresh has occurred
     */
    final class PullToRefreshLoadingError implements NewsViewState {
        private final Throwable error;

        public PullToRefreshLoadingError(Throwable error) {
            this.error = error;
        }

        public Throwable getError() {
            return error;
        }
    }

    /**
     * Indicates that data has been loaded successfully over pull-to-refresh
     */
    final class PullToRefreshLoaded implements NewsViewState {
        private final List<News> data;

        public PullToRefreshLoaded(List<News> data) {
            this.data = data;
        }

        public List<News> getData() {
            return data;
        }
    }
}
