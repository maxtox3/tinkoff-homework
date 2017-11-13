package gusev.max.tinkoff_homework.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gusev.max.tinkoff_homework.data.model.Node;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by v on 13/11/2017.
 */


public class Storage extends SQLiteOpenHelper {

    private static Storage INSTANCE;
    private static final String TAG = Storage.class.getSimpleName();

    public Storage(Context context) {
        super(context, "nodes_db", null, 1);
    }

    public static synchronized Storage getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new Storage(context);
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

    public Flowable<LinkedHashMap<Integer, Byte>> getFilteredNodes() {
        LinkedHashMap<Integer, Byte> filteredNodes = new LinkedHashMap<>();
        //get all nodes
        List<Node> nodeList = getAllNodes();

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

            filteredNodes.put(value, color);
        }
        //filter by value 3,3,2,1...
        filteredNodes = filteredNodes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return Flowable.just(filteredNodes);

    }

    private boolean isParent(int nodeValue) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY_CHECK_CHILDREN, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return check;
    }

    private boolean isChild(int nodeValue) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY_CHECK_PARENTS, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    check = true;
                }
                cursor.close();
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return check;
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
            ID + " integer primary key autoincrement not null," +
            VALUE + " integer not null)";

    //Queries for NodesTable
    private static final String SELECT_ALL_NODES_QUERY = "SELECT * FROM " + NODES_TABLE_NAME;

    //Drop nodes table
    private static final String DROP_NODES_TABLE = "DROP TABLE IF EXISTS " + NODES_TABLE_NAME;


    //<---------------ChildParent Table--------------->

    private static final String CHILD_PARENT_TABLE_NAME = "child_parent_table";
    private static final String PARENT_ID = "parent_id";
    private static final String CHILD_ID = "child_id";

    private static final String CREATE_CHILD_PARENT_TABLE = "create table " + CHILD_PARENT_TABLE_NAME + "(" +
            PARENT_ID + " integer primary key not null," +
            CHILD_ID + " integer primary key not null)";

    //query for check parents existing
    private static final String SELECT_QUERY_CHECK_PARENTS = "SELECT * FROM " + NODES_TABLE_NAME +
            "AS n INNER JOIN " + CHILD_PARENT_TABLE_NAME +
            " as p ON n." + ID + " = p." + PARENT_ID + " WHERE p." + CHILD_ID + "= ?";

    //query for check children existing
    private static final String SELECT_QUERY_CHECK_CHILDREN = "SELECT * FROM " + NODES_TABLE_NAME +
            "AS n INNER JOIN " + CHILD_PARENT_TABLE_NAME +
            " as p ON n." + ID + " = p." + CHILD_ID + " WHERE p." + PARENT_ID + "= ?";

    //Drop ChildParent table
    private static final String DROP_CHILD_PARENT_TABLE = "DROP TABLE IF EXISTS " + CHILD_PARENT_TABLE_NAME;



    /*
    * //если не пусто, то является ребенком
    SELECT * FROM Node AS n INNER JOIN parent_child as p ON n.id = p.parent_id WHERE p.child_id=5

    //Если не пусто, то является родителем
    SELECT * FROM Node AS n INNER JOIN parent_child as p ON n.id = p.child_id WHERE p.parent_id=2
     */
}

