package gusev.max.tinkoff_homework.second;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;

import gusev.max.tinkoff_homework.R;

public class SecondTaskActivity extends AppCompatActivity {

    private final static String TAG = "Second task  ";
    Gson gson;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_task);

        TextView resultTxtView = (TextView) findViewById(R.id.result_text_view);

        buildGson();
        result = gson.toJson(testJson());

        TestClass test = gson.fromJson(result, TestClass.class);

        resultTxtView.setText(test.toString());

        Log.i(TAG, "Any_map instance of HashMap = " + Boolean.toString(test.getMap() instanceof HashMap));

    }

    /**
     * Изначально сделал простой метод который парсит необходимую часть в HashMap,
     * но потом прочитал еще раз задание и увидел, что везде нужен GsonBuilder
     */
    private void buildGson(){
        gson = new GsonBuilder()
                .registerTypeAdapter(TestClass.class, new TestDeserializer())
                .setPrettyPrinting()
                .create();
    }

    private JsonObject testJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "name");

        JsonObject jsonAnyMap = new JsonObject();
        jsonAnyMap.addProperty("a", "55");
        jsonAnyMap.addProperty("b", "85");
        jsonAnyMap.addProperty("c", "56");

        json.add("any_map", jsonAnyMap);

        return json;
    }


}
