package gusev.max.tinkoff_homework.screen.nodes_list;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;

/**
 * Created by v on 13/11/2017.
 */

public class AddNodeDialogFragment extends DialogFragment implements View.OnClickListener {

    NodeListActivityCallback callbacks;
    @BindView(R.id.btnYes)
    Button add;
    @BindView(R.id.btnNo)
    Button no;
    @BindView(R.id.node_value)
    EditText nodeValue;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            callbacks = (NodeListActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement activityCallback");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle("Добавление Node");
        View v = inflater.inflate(R.layout.dialog, null);
        ButterKnife.bind(this, v);
        add.setOnClickListener(this);
        no.setOnClickListener(this);

        return v;
    }

    public void onClick(View v) {
        int num;
        String text = nodeValue.getText().toString();
        try {
            num = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            nodeValue.setError("Введите целое число");
            return;
        }

        Log.d("LOG", "Dialog : " + ((Button) v).getText());
        switch (v.getId()) {
            case R.id.btnYes:
                callbacks.addNode(num);
                break;
            case R.id.btnNo:
                return;
            default:
                break;
        }
        dismiss();
    }
}
