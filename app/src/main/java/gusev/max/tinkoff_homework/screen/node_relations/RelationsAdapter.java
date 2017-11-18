package gusev.max.tinkoff_homework.screen.node_relations;

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
 * Created by v on 15/11/2017.
 */

public class RelationsAdapter extends BaseRecyclerViewAdapter<RelationsAdapter.NodeRelationsViewHolder> {

    class NodeRelationsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.node_relation_txt_view)
        TextView relationTextView;

        NodeRelationsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Node> nodeList;
    private List<Boolean> checkList;
    private int typeOfRelation;
    private Node node;

    RelationsAdapter(Node node) {
        this.node = node;
        this.nodeList = new ArrayList<>();
        this.checkList = new ArrayList<>();
        this.typeOfRelation = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_node, parent, false);
        return new NodeRelationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        NodeRelationsViewHolder vh = (NodeRelationsViewHolder) viewHolder;
        vh.relationTextView.setText(buildStringForTextView(nodeList.get(i)));
        vh.itemView.findViewById(R.id.node_container).setBackgroundColor(getColor(checkList.get(i)));
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    // Package private methods

    /**
     * Replace data to new
     */
    void replaceData(LinkedHashMap<Node, Boolean> map) {

        this.nodeList.clear();
        this.checkList.clear();

        this.nodeList.addAll(map.keySet());
        this.checkList.addAll(map.values());
        notifyDataSetChanged();
    }

    /**
     * Change type of relation
     * 0 if current tab in view is children
     * 1 if current tab in view is parent
     */
    void changeTypeOfRelation(int type) {
        this.typeOfRelation = type;
    }

    int getTypeOfRelation(){
        return typeOfRelation;
    }

    /**
     * Return item by position
     */
    Node getItem(int position) {
        if (position < 0 || position >= nodeList.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return nodeList.get(position);
    }

    /**
     * returns existing of relation
     * true -> exist | false -> no
     */
    Boolean getType(int position) {
        if (position < 0 || position >= checkList.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return checkList.get(position);
    }

    void clearData() {
        nodeList.clear();
        checkList.clear();
        notifyDataSetChanged();
    }

    // Private methods

    private String buildStringForTextView(Node element) {
        if (typeOfRelation == 0) {
            return "id: " + node.getId() + " | value: " + node.getValue() +
                    "  ----  id: " + element.getId() + " | " + "value: " + element.getValue();
        } else {
            return "id: " + element.getId() + " | value: " + element.getValue() +
                    "  ----  id: " + node.getId() + " | " + "value: " + node.getValue();
        }
    }

    private int getColor(boolean check) {
        if (check) {
            return Color.parseColor("#8BC34A");
        }
        return Color.WHITE;
    }
}
