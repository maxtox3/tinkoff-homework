package gusev.max.tinkoff_homework.third;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.math.BigDecimal;

import gusev.max.tinkoff_homework.R;

public class ThirdTaskActivity extends AppCompatActivity {

    Gson gson;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_task);

        buildGson();
        result = gson.toJson(testJson());

        TextView resultTxtView = (TextView) findViewById(R.id.result_text_view);
        BigDecimal bigDecimal = gson.fromJson(result, BigDecimal.class);

        result = getResources().getString(R.string.result) + bigDecimal.toString();
        resultTxtView.setText(result);
    }

    private void buildGson() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(BigDecimal.class, new BigDecimalDeserializer())
                .create();
    }

    private JsonObject testJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("money_amount", "2444,88");
        return jsonObject;
    }
}
