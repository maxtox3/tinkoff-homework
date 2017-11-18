package gusev.max.tinkoff_homework.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import gusev.max.tinkoff_homework.NodeApp;
import gusev.max.tinkoff_homework.data.model.Node;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by v on 13/11/2017.
 */


public class Storage extends SQLiteOpenHelper {

    private static Storage INSTANCE;
    private static final String TAG = Storage.class.getSimpleName();

    private Storage(Context context) {
        super(context, "nodes_db", null, 1);
    }

    //As singleton
    public static synchronized Storage getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new Storage(NodeApp.getContext());
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        try {
            db.execSQL(CREATE_NODES_TABLE);
            db.execSQL(CREATE_CHILD_PARENT_TABLE);
        } catch (SQLException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop tables then recreate
        db.execSQL(DROP_NODES_TABLE);
        db.execSQL(DROP_CHILD_PARENT_TABLE);
        onCreate(db);
    }

    /**
     * Add new node
     *
     * @param value of node
     * @return Completable
     */
    public Completable addNode(int value) {
        return Completable.fromCallable(() -> {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(VALUE, value);

            try {
                db.insert(NODES_TABLE_NAME, null, values);
            } catch (SQLException e) {
                Log.i(TAG, e.getMessage());
            }

            return Completable.complete();
        });
    }

    /**
     * Get filtered nodes with colors for 1 screen
     *
     * @return filtered LinkedHashMap with list of nodes as key set and colors list as value set
     */
    public Single<LinkedHashMap<Node, Byte>> getFilteredNodes() {
        LinkedHashMap<Node, Byte> filteredNodes = new LinkedHashMap<>();
        //get all nodes
        List<Node> nodeList = getAllNodes();

        List<Node> filteredNodesList = new ArrayList<>();
        List<Byte> filteredColorsArray = new ArrayList<>();

        //then for each node check if hasParent/hasChild -> set number for color
        for (Node node : nodeList) {

            byte color = 0;//none
            long id = node.getId();
            boolean parent = false;
            boolean child = false;

            if (isParent(id)) {
                color = 1;//yellow
                parent = true;
            }

            if (isChild(id)) {
                color = 2;//blue
                child = true;
            }

            if (parent && child) {
                color = 3;//red
            }

            filteredNodesList.add(node);
            filteredColorsArray.add(color);
        }

        for (int i = 0; i < filteredNodesList.size(); i++) {
            filteredNodes.put(filteredNodesList.get(i), filteredColorsArray.get(i));
        }

        return Single.just(filteredNodes);

    }

    /**
     * Get a list of children of a specific element
     *
     * @param parentNodeId node-parent id
     * @return LinkedHashMap with list of children nodes as key set and list of existing relations to each element
     */
    public Single<LinkedHashMap<Node, Boolean>> getNodesWithChildRelations(long parentNodeId) {
        LinkedHashMap<Node, Boolean> nodesWithRelations = new LinkedHashMap<>();
        List<Node> nodeList = getAllPossibleRelations(parentNodeId);

        for (Node node : nodeList) {
            //check for existing relation
            // if true -> continue
            // else -> add to map to show
            long[] checkChildRelationNodes = new long[]{node.getId(), parentNodeId};
            if (checkRelation(checkChildRelationNodes)) {
                continue;
            }

            long[] nodes = new long[]{parentNodeId, node.getId()};
            if (checkRelation(nodes)) {
                nodesWithRelations.put(node, true);
            } else {
                nodesWithRelations.put(node, false);
            }
        }
        return Single.just(nodesWithRelations);
    }

    /**
     * Get a list of parents of a specific element
     *
     * @param childNodeId node-child id
     * @return LinkedHashMap with list of parents nodes as key set and list of existing relations to each element
     */
    public Single<LinkedHashMap<Node, Boolean>> getNodesWithParentRelations(long childNodeId) {
        LinkedHashMap<Node, Boolean> nodesWithRelations = new LinkedHashMap<>();
        List<Node> nodeList = getAllPossibleRelations(childNodeId);

        for (Node node : nodeList) {
            //check for existing relation
            // if true -> continue
            // else -> add to map to show
            long[] checkChildRelationNodes = new long[]{childNodeId, node.getId()};
            if (checkRelation(checkChildRelationNodes)) {
                continue;
            }

            long[] nodes = new long[]{node.getId(), childNodeId};
            if (checkRelation(nodes)) {
                nodesWithRelations.put(node, true);
            } else {
                nodesWithRelations.put(node, false);
            }
        }
        return Single.just(nodesWithRelations);
    }

    //<--------------------Creating and deleting relation methods-------------------->
    /**
     * Add relation between 2 nodes
     *
     * @param nodeIdFirst  id of first node
     * @param nodeIdSecond id of second node
     * @return Completable
     */
    public Completable addRelation(long nodeIdFirst, long nodeIdSecond) {
        return Completable.fromCallable(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            if (!ifExistsRelation(nodeIdFirst, nodeIdSecond, db)) {
                ContentValues values = new ContentValues();
                values.put(PARENT_ID, nodeIdFirst);
                values.put(CHILD_ID, nodeIdSecond);

                try {
                    db.insert(CHILD_PARENT_TABLE_NAME, null, values);
                } catch (SQLException e) {
                    Log.i(TAG, e.getMessage());
                }
            }
            return Completable.complete();
        });
    }

    /**
     * Remove relation between 2 nodes
     *
     * @param nodeIdFirst  id of first node
     * @param nodeIdSecond id of second node
     * @return Completable
     */
    public Completable removeRelation(long nodeIdFirst, long nodeIdSecond) {
        return Completable.fromCallable(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            if (ifExistsRelation(nodeIdFirst, nodeIdSecond, db)) {

                try {
                    db.delete(CHILD_PARENT_TABLE_NAME,
                            "parent_id = ? AND child_id = ?",
                            new String[]{String.valueOf(nodeIdFirst),
                                    String.valueOf(nodeIdSecond)});
                } catch (SQLException e) {
                    Log.i(TAG, e.getMessage());
                }
            }
            return Completable.complete();
        });
    }

    //<--------------------Get nodes private methods-------------------->

    private List<Node> getAllPossibleRelations(long nodeId) {
        return getNodes(
                "SELECT n.id, n.value FROM NODES_TABLE AS n INNER JOIN NODES_TABLE AS m ON n.id = m.id WHERE m.id != ?",
                new String[]{String.valueOf(nodeId)});
    }

    private List<Node> getAllNodes() {
        return getNodes(
                "SELECT * FROM NODES_TABLE",
                null);
    }

    private List<Node> getNodes(String query, String[] selectionArgs) {
        List<Node> nodeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Node node = new Node();
                            node.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                            node.setValue(cursor.getInt(cursor.getColumnIndex(VALUE)));
                            node.setNodes(new ArrayList<>());

                            nodeList.add(node);
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.i(TAG, e.getMessage());
        }
        return nodeList;
    }

    //<--------------------Check methods-------------------->

    private boolean checkRelation(long[] nodes) {
        return checkQuery(
                "SELECT * FROM CHILD_PARENT_TABLE WHERE PARENT_ID = ? AND CHILD_ID = ?",
                new String[]{String.valueOf(nodes[0]), String.valueOf(nodes[1])}
        );
    }

    private boolean isParent(long nodeId) {
        return checkQuery(
                "SELECT * FROM NODES_TABLE AS n INNER JOIN CHILD_PARENT_TABLE AS p ON n.id = p.child_id WHERE p.parent_id = ?",
                new String[]{String.valueOf(nodeId)}
        );
    }

    private boolean isChild(long nodeId) {
        return checkQuery(
                "SELECT * FROM NODES_TABLE AS n INNER JOIN CHILD_PARENT_TABLE AS p ON n.id = p.parent_id WHERE p.child_id = ?",
                new String[]{String.valueOf(nodeId)}
        );
    }

    private boolean ifExistsRelation(long nodeIdFirst, long nodeIdSecond, SQLiteDatabase db) {
        return checkQuery(
                "SELECT * FROM CHILD_PARENT_TABLE WHERE parent_id = ? AND child_id = ?",
                new String[]{String.valueOf(nodeIdFirst), String.valueOf(nodeIdSecond)}
        );
    }

    /**
     * Check query
     *
     * @param query         String SQL query
     * @param selectionArgs String[] args
     * @return boolean
     */
    private boolean checkQuery(String query, String[] selectionArgs) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.i(TAG, e.getMessage());
        }
        return check;
    }

    //<---------------Nodes Table--------------->

    private static final String NODES_TABLE_NAME = "nodes_table";
    private static final String ID = "id";
    private static final String VALUE = "value";

    private static final String CREATE_NODES_TABLE = "create table " + NODES_TABLE_NAME + "(" +
            ID + " integer not null," +
            VALUE + " integer not null, " +
            "PRIMARY KEY (" + ID + "))";

    //Drop nodes table
    private static final String DROP_NODES_TABLE = "DROP TABLE IF EXISTS " + NODES_TABLE_NAME;


    //<---------------ChildParent Table--------------->

    private static final String CHILD_PARENT_TABLE_NAME = "child_parent_table";
    private static final String PARENT_ID = "parent_id";
    private static final String CHILD_ID = "child_id";

    //Create ChildParent table
    private static final String CREATE_CHILD_PARENT_TABLE = "create table " + CHILD_PARENT_TABLE_NAME + "(" +
            PARENT_ID + " int not null," +
            CHILD_ID + " int not null, " +
            "UNIQUE (" + PARENT_ID + "," + CHILD_ID + "))";

    //Drop ChildParent table
    private static final String DROP_CHILD_PARENT_TABLE = "DROP TABLE IF EXISTS " + CHILD_PARENT_TABLE_NAME;
}

