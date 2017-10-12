package gusev.max.tinkoff_homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import gusev.max.tinkoff_homework.firstPart.FirstPartActivity;
import gusev.max.tinkoff_homework.secondPart.SecondPartActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button firstPartButton;
    Button secondPartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstPartButton = (Button) findViewById(R.id.first_part_button);
        secondPartButton = (Button) findViewById(R.id.second_part_button);

        firstPartButton.setOnClickListener(this);
        secondPartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {

            case R.id.first_part_button:
                intent = new Intent(this, FirstPartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.second_part_button:
                intent = new Intent(this, SecondPartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
