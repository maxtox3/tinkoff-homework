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

    private List<Node> nodesList;
    private List<Byte> nodesColors;

    NodesListAdapter() {
        this.nodesList = new ArrayList<>();
        this.nodesColors = new ArrayList<>();
    }

    // methods of super
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
        NodesListViewHolder vh = (NodesListViewHolder) viewHolder;
        vh.valueTxtView.setText(buildValueForTextView(i));
        vh.itemView.findViewById(R.id.node_container).setBackgroundColor(getColor(nodesColors.get(i)));
    }

    @Override
    public int getItemCount() {
        return nodesList.size();
    }

    // Package private methods

    /**
     * Replace data to new
     */
    void replaceData(LinkedHashMap<Node, Byte> map) {
        this.nodesList.addAll(map.keySet());
        this.nodesColors.addAll(map.values());
        notifyDataSetChanged();
    }

    /**
     * Return item by position
     */
    Node getItem(int position) {
        if (position < 0 || position >= nodesList.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return nodesList.get(position);
    }

    void clearData() {
        nodesList.clear();
        nodesColors.clear();
        notifyDataSetChanged();
    }

    // Private methods

    private String buildValueForTextView(int i){
        return "id: " + String.valueOf(nodesList.get(i).getId()) + " | value: " + String.valueOf(nodesList.get(i).getValue());
    }

    private int getColor(byte color) {

        switch (color) {

            case 0:
                return Color.parseColor("#FFFFFF");// White color

            case 1:
                return Color.parseColor("#FFEB3B");// Yellow color

            case 2:
                return Color.parseColor("#2196F3");// Blue color

            case 3:
                return Color.parseColor("#F44336");// Red color

            default:
                break;
        }
        return Color.parseColor("#FFFFFF");
    }
}
