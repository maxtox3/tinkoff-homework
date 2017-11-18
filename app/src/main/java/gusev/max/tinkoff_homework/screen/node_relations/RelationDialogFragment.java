package gusev.max.tinkoff_homework.screen.node_relations;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import gusev.max.tinkoff_homework.R;

/**
 * Created by v on 16/11/2017.
 */

public class RelationDialogFragment extends DialogFragment {

    RelationsActivityCallback callbacks;

    private long nodeId;
    private boolean type;

    public interface RelationsActivityCallback {
        void onClickOkButton(long nodeId, boolean type);
    }

    static RelationDialogFragment newInstance(long nodeId, boolean type) {
        RelationDialogFragment f = new RelationDialogFragment();

        Bundle args = new Bundle();
        args.putBoolean("type", type);
        args.putLong("nodeId", nodeId);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (RelationsActivityCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement activityCallback");
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        nodeId = getArguments().getLong("nodeId");
        type = getArguments().getBoolean("type");
        String title;

        if(type){
            title = "Remove relation?";
        } else {
            title = "Add relation?";
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    if (callbacks != null) {
                        callbacks.onClickOkButton(nodeId, type);
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
