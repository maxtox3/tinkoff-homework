package gusev.max.tinkoff_homework.screen.nodes_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;
import gusev.max.tinkoff_homework.screen.base.BaseRecyclerViewAdapter;
import io.reactivex.annotations.NonNull;

/**
 * Created by v on 13/11/2017.
 */

public class NodesListAdapter extends BaseRecyclerViewAdapter<NodesListAdapter.NodesListViewHolder> {

    class NodesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.node_value_txt_view)
        TextView valueTxtView;
        public NodesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private LinkedHashMap<Integer, Byte> nodes;
    private List<Integer> nodesValues;
    private List<Byte> nodesColors;

    public NodesListAdapter(@NonNull LinkedHashMap<Integer, Byte> map) {
        this.nodes = map;
        this.nodesValues = new ArrayList<>();
        this.nodesColors = new ArrayList<>();
    }

    @Override
    public NodesListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_node, viewGroup, false);
        return new NodesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        NodesListViewHolder vh = (NodesListViewHolder) viewHolder; //safe cast
        String value = nodesValues.get(i).toString();//for debug
        vh.valueTxtView.setText(value);
        vh.itemView.findViewById(R.id.card_view).setBackgroundColor(getColor(nodesColors.get(i)));
    }

    @Override
    public int getItemCount() {
        return nodesValues.size();
    }

    private int getColor(byte color){

        switch (color){

            case 0:
                return R.color.white;

            case 1:
                return R.color.yellow;

            case 2:
                return R.color.blue;

            case 3:
                return R.color.red;

            default:
                break;
        }
        return 0;
    }

    public void   replaceData(LinkedHashMap<Integer, Byte> map) {
        this.nodes.clear();
        this.nodes = map;
        this.nodesValues.addAll(map.keySet());
        this.nodesColors.addAll(map.values());
        notifyDataSetChanged();
    }

    public Integer getItem(int position) {
        if (position < 0 || position >= nodesValues.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return nodesValues.get(position);
    }

    public void clearData() {
        nodesValues.clear();
        nodesColors.clear();
        nodes.clear();
        notifyDataSetChanged();
    }
}
