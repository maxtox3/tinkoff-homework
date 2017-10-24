package gusev.max.tinkoff_homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import gusev.max.tinkoff_homework.first.FirstTaskActivity;
import gusev.max.tinkoff_homework.second.SecondTaskActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button first = (Button) findViewById(R.id.to_first_part_button);
        first.setOnClickListener(this);
        Button second = (Button) findViewById(R.id.to_second_part_button);
        second.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.to_first_part_button:
                intent = new Intent(this, FirstTaskActivity.class);
                startActivity(intent);
                break;

            case R.id.to_second_part_button:
                intent = new Intent(this, SecondTaskActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
