package gusev.max.tinkoff_homework.first;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gusev.max.tinkoff_homework.R;

public class FirstTaskActivity extends AppCompatActivity {

    private final String fullPathToField = "gusev.max.tinkoff_homework.first.Developer.id";

    Gson gson;
    TextView jsonTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_task);

        jsonTxtView = (TextView) findViewById(R.id.result_text_view);

        Language myLang = new Language(
                2L,
                "Java"
        );

        Developer me = new Developer(
                1L,
                "Max",
                "maxtox3",
                "https://github.com/maxtox3",
                myLang,
                "android development"
        );

        buildGson();

        String json = gson.toJson(me);
        json = " Исключили поле : " + fullPathToField + "\n\n" + json;
        jsonTxtView.setText(json);

    }

    private void buildGson() {
        try {
            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .setExclusionStrategies(new MyExclusionStrategy(fullPathToField))
                    //.serializeNulls() //раскомментить, если пустые тоже сериализовывать
                    .create();
        } catch (NoSuchFieldException e) {
            Toast.makeText(this, "Нет такого поля, перепроверь", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Нет такого класса, перепроверь", Toast.LENGTH_SHORT).show();
        }
    }
}
