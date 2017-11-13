package gusev.max.tinkoff_homework.data.model;

import java.util.List;

/**
 * Created by v on 13/11/2017.
 */

public class Node {

    private long id;
    private int value;
    private List<Node> nodes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
