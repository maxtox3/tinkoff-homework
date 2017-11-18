package gusev.max.tinkoff_homework.screen.base;

import android.support.v7.widget.RecyclerView;

import io.reactivex.annotations.NonNull;

/**
 * A general RecyclerViewAdapter which supports OnItemClickListener and OnItemLongClickListener.
 *
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private RecyclerViewListener.OnItemClickListener itemClickListener;

  public void setOnItemClickListener(
      @NonNull RecyclerViewListener.OnItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }


  /**
   * Override onBindViewHolder to support OnItemClick and OnItemLongClick listener.
   */
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
      if (itemClickListener != null) {
        viewHolder.itemView.setOnClickListener(view -> itemClickListener.OnItemClick(view, i));
      }
  }
}
