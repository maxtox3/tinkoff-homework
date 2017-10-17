package gusev.max.tinkoff_homework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import gusev.max.tinkoff_homework.R;

import static gusev.max.tinkoff_homework.Utils.isEditTextEmpty;
import static gusev.max.tinkoff_homework.Utils.isNumeric;
import static gusev.max.tinkoff_homework.Utils.makeToast;

/**
 * Created by v on 16/10/2017.
 */

public class FirstFragment extends BaseFragment {

    EditText editText1;
    String text;
    Button button1;

    private static String BUNDLE_NUMBER1_KEY = "BUNDLE_NUMBER1_KEY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        editText1 = view.findViewById(R.id.edit_text_1);
        button1 = view.findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText1.getText().toString();
                if (isNumeric(text)) {
                    activityCallback.startSecondFragment(Double.parseDouble(text));
                } else {
                    editText1.setText("");
                    makeToast(view);
                }

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            text = (String) savedInstanceState.getSerializable(BUNDLE_NUMBER1_KEY);
        }

        if(!isEditTextEmpty(text)){
            editText1.setText(text);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isEditTextEmpty(text)) {
            outState.putSerializable(BUNDLE_NUMBER1_KEY, text);
        }
    }
}
