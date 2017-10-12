package gusev.max.tinkoff_homework.secondPart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gusev.max.tinkoff_homework.R;

public class SecondPartActivity extends AppCompatActivity implements View.OnClickListener {

    Button acceptButton;
    EditText nameEditText;
    private String teacherName;

    private final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_part);

        acceptButton = (Button) findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(this);

        nameEditText = (EditText) findViewById(R.id.message_edit_name);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SPSecondActivity.class);
        teacherName = nameEditText.getText().toString();
        intent.putExtra(NAME, teacherName);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:
                makeToast(teacherName + getResources().getString(R.string.best_teacher));
            break;
            case RESULT_CANCELED:
                makeToast(getResources().getString(R.string.cancel));

        }
    }

    private void makeToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
