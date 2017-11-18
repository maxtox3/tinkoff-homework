package gusev.max.tinkoff_homework.screen.base;

import android.view.View;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    void OnItemClick(View view, int position);
  }
}
