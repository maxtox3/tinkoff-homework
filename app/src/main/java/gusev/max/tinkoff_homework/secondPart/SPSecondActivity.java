package gusev.max.tinkoff_homework.secondPart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gusev.max.tinkoff_homework.R;

public class SPSecondActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nameTextView;
    Button okButton;
    Button cancelButton;

    private final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spsecond);

        nameTextView = (TextView) findViewById(R.id.name_text_view);
        nameTextView.setText(getIntent().getStringExtra(NAME));

        okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ok_button:
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.cancel_button:
                setResult(RESULT_CANCELED);
                finish();
                break;

            default:
                break;
        }
    }
}
