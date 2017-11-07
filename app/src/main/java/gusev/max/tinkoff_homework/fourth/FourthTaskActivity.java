package gusev.max.tinkoff_homework.fourth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gusev.max.tinkoff_homework.R;

public class FourthTaskActivity extends AppCompatActivity {

    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_task);

        buildGson();

        TextView resultTxtView = (TextView) findViewById(R.id.result_text_view);

        String json = gson.toJson(DateExample.getTestDate(), DateExample.class);

        resultTxtView.setText(json);
    }

    private void buildGson(){
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd")
                .create();
    }
}
