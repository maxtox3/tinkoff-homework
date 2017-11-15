package gusev.max.tinkoff_homework.screen.node_relations;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private long node;

    RelationsAdapter(long nodeId){
        this.node = nodeId;
        this.nodeList = new ArrayList<>();
        this.checkList = new ArrayList<>();
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
        vh.relationTextView.setText(
                buildStringForTextView(
                        String.valueOf(
                                nodeList.get(i).getValue()
                        )
                )
        );
        vh.itemView.findViewById(R.id.node_container).setBackgroundColor(getColor(checkList.get(i)));
        //todo здесь закидываем в вьюшку текст
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    void replaceData(LinkedHashMap<Node, Boolean> map){
        this.nodeList.addAll(map.keySet());
        this.checkList.addAll(map.values());
        notifyDataSetChanged();
    }

    private String buildStringForTextView(String element){
        return node + "  ----  " + element;
    }

    void clearData() {
        nodeList.clear();
        checkList.clear();
        notifyDataSetChanged();
    }

    private int getColor(boolean check){
        if(check){
            return Color.GREEN;
        }
        return Color.WHITE;
    }
}
