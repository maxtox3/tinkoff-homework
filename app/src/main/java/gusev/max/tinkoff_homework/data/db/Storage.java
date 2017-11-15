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
        super(context, "nodes_db", null, 2);
    }

    public static synchronized Storage getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new Storage(NodeApp.getContext());
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_NODES_TABLE);
            db.execSQL(CREATE_CHILD_PARENT_TABLE);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_NODES_TABLE);
        db.execSQL(DROP_CHILD_PARENT_TABLE);
        onCreate(db);
    }

    public Completable addNode(int value) {
        return Completable.fromCallable(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            if (!ifExists(value, db)) {
                ContentValues values = new ContentValues();
                values.put(VALUE, value);

                try {
                    db.insert(NODES_TABLE_NAME, null, values);
                } catch (SQLException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            db.close();
            return Completable.complete();
        });
    }

    public Single<LinkedHashMap<Node, Byte>> getFilteredNodes() {
        LinkedHashMap<Node, Byte> filteredNodes = new LinkedHashMap<>();
        //get all nodes
        List<Node> nodeList = getAllNodes();

        List<Node> filteredNodesList = new ArrayList<>();
        List<Byte> filteredColorsArray = new ArrayList<>();

        //then for each node check if hasParent/hasChild -> set number dor color
        for (Node node : nodeList) {

            byte color = 0;//none
            int value = node.getValue();
            boolean parent = false;
            boolean child = false;

            if (isParent(value)) {
                color = 1;//yellow
                parent = true;
            }

            if (isChild(value)) {
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

    public Single<LinkedHashMap<Node, Boolean>> getNodesWithChildRelations(long parentNodeId) {
        LinkedHashMap<Node, Boolean> nodesWithRelations = new LinkedHashMap<>();
        List<Node> nodeList = getAllPossibleRelations(parentNodeId);

        for (Node node : nodeList){
            if(checkRelation(parentNodeId, node.getId())){
                nodesWithRelations.put(node, true);
            }
            nodesWithRelations.put(node, false);
        }
        return Single.just(nodesWithRelations);
    }

    public Single<LinkedHashMap<Node, Boolean>> getNodesWithParentRelations(long childNodeId) {
        LinkedHashMap<Node, Boolean> nodesWithRelations = new LinkedHashMap<>();
        List<Node> nodeList = getAllPossibleRelations(childNodeId);

        for (Node node : nodeList){
            if(checkRelation(node.getId(), childNodeId)){
                nodesWithRelations.put(node, true);
            }
            nodesWithRelations.put(node, false);
        }
        return Single.just(nodesWithRelations);
    }

    private boolean checkRelation(long childNodeId, long parentNodeId){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //todo переписать db.rawQuery() и из 3 методов сделать 1
            Cursor cursor = db.rawQuery(buildQueryCheckRelation(childNodeId,parentNodeId), null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();
        return check;
    }

    private boolean isParent(long nodeId) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY_CHECK_CHILDREN + nodeId, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();
        return check;
    }

    private boolean isChild(long nodeId) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY_CHECK_PARENTS + nodeId, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();
        return check;
    }

    private List<Node> getAllPossibleRelations(long nodeId) {
        List<Node> nodeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            //todo сделать из 2 методов - 1
            Cursor cursor = db.rawQuery(SELECT_QUERY_POSSIBLE_RELATIONS + nodeId, null);
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
            Log.d(TAG, e.getMessage());
        }
        db.close();
        return nodeList;
    }

    private List<Node> getAllNodes() {
        List<Node> nodeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_ALL_NODES_QUERY, null);
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
            Log.d(TAG, e.getMessage());
        }
        db.close();
        return nodeList;
    }

    private boolean ifExists(int node, SQLiteDatabase db) {
        Cursor cursor;
        String checkQuery = "SELECT " + ID + " FROM " + NODES_TABLE_NAME + " WHERE " + ID + "= '" + node + "'";
        cursor = db.rawQuery(checkQuery, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    //<---------------Nodes Table--------------->

    private static final String NODES_TABLE_NAME = "nodes_table";
    private static final String ID = "id";
    private static final String VALUE = "value";

    private static final String CREATE_NODES_TABLE = "create table " + NODES_TABLE_NAME + "(" +
            ID + " integer not null," +
            VALUE + " integer not null, " +
            "PRIMARY KEY (" + ID + "))";

    //Queries for NodesTable
    private static final String SELECT_ALL_NODES_QUERY = "SELECT * FROM " + NODES_TABLE_NAME;

    //Drop nodes table
    private static final String DROP_NODES_TABLE = "DROP TABLE IF EXISTS " + NODES_TABLE_NAME;


    //<---------------ChildParent Table--------------->

    private static final String CHILD_PARENT_TABLE_NAME = "child_parent_table";
    private static final String PARENT_ID = "parent_id";
    private static final String CHILD_ID = "child_id";

    private static final String CREATE_CHILD_PARENT_TABLE = "create table " + CHILD_PARENT_TABLE_NAME + "(" +
            PARENT_ID + " int not null," +
            CHILD_ID + " int not null, " +
            "UNIQUE (" + PARENT_ID + "," + CHILD_ID + "))";

    //query for check parents existing
    private static final String SELECT_QUERY_CHECK_PARENTS = "SELECT * FROM " + NODES_TABLE_NAME +
            " AS n INNER JOIN " + CHILD_PARENT_TABLE_NAME +
            " as p ON n." + ID + " = p." + PARENT_ID + " WHERE p." + CHILD_ID + "= ";

    //query for check children existing
    private static final String SELECT_QUERY_CHECK_CHILDREN = "SELECT * FROM " + NODES_TABLE_NAME +
            " AS n INNER JOIN " + CHILD_PARENT_TABLE_NAME +
            " as p ON n." + ID + " = p." + CHILD_ID + " WHERE p." + PARENT_ID + "= ";

    //query that returning all possible variants of relations
    private static final String SELECT_QUERY_POSSIBLE_RELATIONS = "SELECT n." + ID + ", n." + VALUE +
            " FROM " + NODES_TABLE_NAME + " AS n INNER JOIN " +
            NODES_TABLE_NAME + " AS m ON n." + ID + " = m." + ID + " WHERE m." + ID + " != ";

    //query that checks relation
    private String buildQueryCheckRelation(long parentId, long childId){
        return "SELECT * FROM " + CHILD_PARENT_TABLE_NAME +
                " WHERE " + PARENT_ID + " = " + parentId + " AND " + CHILD_ID + " = " + childId;
    }

    //Drop ChildParent table
    private static final String DROP_CHILD_PARENT_TABLE = "DROP TABLE IF EXISTS " + CHILD_PARENT_TABLE_NAME;



    /*
    * //если не пусто, то является ребенком
    SELECT * FROM Node AS n INNER JOIN parent_child as p ON n.id = p.parent_id WHERE p.child_id=5

    //Если не пусто, то является родителем
    SELECT * FROM Node AS n INNER JOIN parent_child as p ON n.id = p.child_id WHERE p.parent_id=2

    //Выдет список id:value, включающий в себя все возможные варианты связей
    SELECT n.id, n.value FROM Node AS n INNER JOIN Node as m ON n.id = m.id WHERE m.id!=1

    //Тащим всех детей по опред айди родителя
    SELECT parent_child.parent_id, parent_child.child_id FROM parent_child WHERE parent_child.parent_id=1

    //проверяем для каждого есть ли такое в таблице parent_child
    SELECT * FROM parent_child WHERE parent_id = 2 AND child_id = 5

     */

}

