package gusev.max.tinkoff_homework.firstPart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gusev.max.tinkoff_homework.R;

public class FirstPartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    Button sendButton;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_part);

        editText = (EditText) findViewById(R.id.message_edit_text);

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent sendIntent = new Intent();
        message = editText.getText().toString();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        startActivity(sendIntent);
    }
}
