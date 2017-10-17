package gusev.max.tinkoff_homework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gusev.max.tinkoff_homework.R;

/**
 * Created by v on 16/10/2017.
 */

public class ThirdFragment extends BaseFragment implements View.OnClickListener{

    Button plusButton;
    Button minusButton;
    Button multiplyButton;
    Button divideButton;

    private static String BUNDLE_SYMBOL_KEY = "BUNDLE_SYMBOL_KEY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        plusButton = view.findViewById(R.id.plus_button);
        plusButton.setOnClickListener(this);
        minusButton = view.findViewById(R.id.minus_button);
        minusButton.setOnClickListener(this);
        multiplyButton = view.findViewById(R.id.multiply_button);
        multiplyButton.setOnClickListener(this);
        divideButton = view.findViewById(R.id.divide_button);
        divideButton.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.plus_button:
                activityCallback.startFourthFragment("+");
                break;
            case R.id.minus_button:
                activityCallback.startFourthFragment("-");
                break;
            case R.id.multiply_button:
                activityCallback.startFourthFragment("*");
                break;
            case R.id.divide_button:
                activityCallback.startFourthFragment("/");
                break;
        }
        /*
        *todo 1)доделать 3 фрагмент
        *todo 2)продумать, надо ли через конструктор инит нового инстанса с параметрами, или тупо из бандла тащить
        *todo 3)запилить шапку с бэкстэком
        */
    }
}
