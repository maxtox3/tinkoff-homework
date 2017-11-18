package gusev.max.tinkoff_homework.screen.nodes_list;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import gusev.max.tinkoff_homework.R;

/**
 * Created by v on 13/11/2017.
 */

public class AddNodeDialogFragment extends DialogFragment {

    private NodeListActivityCallback callbacks;

    @BindView(R.id.node_value)
    EditText nodeValue;
    @BindView(R.id.text_input_layout)
    TextInputLayout textInputLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (NodeListActivityCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);
        ButterKnife.bind(this, view);

        nodeValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (text.isEmpty()) {
                    textInputLayout.setError(getString(R.string.empty));
                } else {
                    textInputLayout.setError("");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Add new Node:")
                .setView(view)
                .setPositiveButton(R.string.add, (dialogInterface, i) -> {
                    if (callbacks != null) {
                        callbacks.addNode(Integer.parseInt(nodeValue.getText().toString()));
                    }
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dismiss());
        return adb.create();
    }

    @Override
    public void onDetach() {
        callbacks = null;
        super.onDetach();
    }
}
