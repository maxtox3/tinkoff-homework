package gusev.max.tinkoff_homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import gusev.max.tinkoff_homework.first.FirstTaskActivity;
import gusev.max.tinkoff_homework.fourth.FourthTaskActivity;
import gusev.max.tinkoff_homework.second.SecondTaskActivity;
import gusev.max.tinkoff_homework.third.ThirdTaskActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button first = (Button) findViewById(R.id.first_task_button);
        first.setOnClickListener(this);

        Button second = (Button) findViewById(R.id.second_task_button);
        second.setOnClickListener(this);

        Button third = (Button) findViewById(R.id.third_task_button);
        third.setOnClickListener(this);

        Button fourth = (Button) findViewById(R.id.fourth_task_button);
        fourth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_task_button:
                startActivity(new Intent(this, FirstTaskActivity.class));
                break;

            case R.id.second_task_button:
                startActivity(new Intent(this, SecondTaskActivity.class));
                break;

            case R.id.third_task_button:
                startActivity(new Intent(this, ThirdTaskActivity.class));
                break;

            case R.id.fourth_task_button:
                startActivity(new Intent(this, FourthTaskActivity.class));
                break;

            default:
                break;
        }
    }
}
