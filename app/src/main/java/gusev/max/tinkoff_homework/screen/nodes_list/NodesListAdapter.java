package gusev.max.tinkoff_homework.screen.nodes_list;

import android.graphics.Color;
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
import gusev.max.tinkoff_homework.data.model.Node;
import gusev.max.tinkoff_homework.screen.base.BaseRecyclerViewAdapter;
import io.reactivex.annotations.NonNull;

/**
 * Created by v on 13/11/2017.
 */

public class NodesListAdapter extends BaseRecyclerViewAdapter<NodesListAdapter.NodesListViewHolder> {

    class NodesListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.node_value_txt_view)
        TextView valueTxtView;

        NodesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private LinkedHashMap<Node, Byte> nodes;
    private List<Node> nodesList;
    private List<Byte> nodesColors;

    NodesListAdapter(@NonNull LinkedHashMap<Node, Byte> map) {
        this.nodes = map;
        this.nodesList = new ArrayList<>();
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
        String value = String.valueOf(nodesList.get(i).getValue());//for debug
        vh.valueTxtView.setText(value);
        vh.itemView.findViewById(R.id.node_container).setBackgroundColor(getColor(nodesColors.get(i)));
    }

    @Override
    public int getItemCount() {
        return nodesList.size();
    }

    private int getColor(byte color) {

        switch (color) {

            case 0:
                return Color.parseColor("#FFFFFF");

            case 1:
                return Color.parseColor("#FFEB3B");

            case 2:
                return Color.parseColor("#2196F3");

            case 3:
                return Color.parseColor("#F44336");

            default:
                break;
        }
        return Color.parseColor("#FFFFFF");
    }

    void replaceData(LinkedHashMap<Node, Byte> map) {
        this.nodes.clear();
        this.nodes = map;
        this.nodesList.addAll(map.keySet());
        this.nodesColors.addAll(map.values());
        notifyDataSetChanged();
    }

    Node getItem(int position) {
        if (position < 0 || position >= nodesList.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return nodesList.get(position);
    }

    void clearData() {
        nodesList.clear();
        nodesColors.clear();
        nodes.clear();
        notifyDataSetChanged();
    }
}
