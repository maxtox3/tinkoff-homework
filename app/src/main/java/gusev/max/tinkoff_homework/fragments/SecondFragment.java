package gusev.max.tinkoff_homework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import gusev.max.tinkoff_homework.R;

import static gusev.max.tinkoff_homework.utils.Utils.isEditTextEmpty;
import static gusev.max.tinkoff_homework.utils.Utils.isNumeric;
import static gusev.max.tinkoff_homework.utils.Utils.makeToast;

/**
 * Created by v on 16/10/2017.
 */

public class SecondFragment extends BaseFragment {

    EditText editText2;
    String text;
    Button button2;

    private static String BUNDLE_NUMBER2_KEY = "BUNDLE_NUMBER2_KEY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        editText2 = view.findViewById(R.id.edit_text_2);
        button2 = view.findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText2.getText().toString();
                if (isNumeric(text)) {
                    activityCallback.startThirdFragment(Double.parseDouble(text));
                } else {
                    editText2.setText("");
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
            text = (String) savedInstanceState.getSerializable(BUNDLE_NUMBER2_KEY);
        }

        if(!isEditTextEmpty(text)){
            editText2.setText(text);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isEditTextEmpty(text)) {
            outState.putSerializable(BUNDLE_NUMBER2_KEY, text);
        }
    }
}
