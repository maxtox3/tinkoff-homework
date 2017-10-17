package gusev.max.tinkoff_homework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gusev.max.tinkoff_homework.R;

import static gusev.max.tinkoff_homework.Utils.isEditTextEmpty;

/**
 * Created by v on 16/10/2017.
 */

public class FourthFragment extends Fragment {

    private TextView expressionTextView;
    private String expression;

    private static String BUNDLE_EXPRESSION_KEY = "BUNDLE_EXPRESSION_KEY";

    public static FourthFragment newInstance(String expression) {
        FourthFragment myFragment = new FourthFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_EXPRESSION_KEY, expression);
        myFragment.setArguments(args);

        return myFragment;
    }

    private String getExpression() {
        return String.valueOf(getArguments().getSerializable(BUNDLE_EXPRESSION_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        expressionTextView = view.findViewById(R.id.expression_edit_text);
        expression = getExpression();
        expressionTextView.setText(expression);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            expression = (String) savedInstanceState.getSerializable(BUNDLE_EXPRESSION_KEY);
        }

        if (!isEditTextEmpty(expression)) {
            expressionTextView.setText(expression);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isEditTextEmpty(expression)) {
            outState.putSerializable(BUNDLE_EXPRESSION_KEY, expression);
        }
    }
}
