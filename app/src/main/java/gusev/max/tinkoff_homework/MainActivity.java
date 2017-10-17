package gusev.max.tinkoff_homework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import gusev.max.tinkoff_homework.fragments.FirstFragment;
import gusev.max.tinkoff_homework.fragments.FourthFragment;
import gusev.max.tinkoff_homework.fragments.SecondFragment;
import gusev.max.tinkoff_homework.fragments.ThirdFragment;
import gusev.max.tinkoff_homework.utils.Calc;

public class MainActivity extends AppCompatActivity implements ActivityCallback{

    private static String TAG = "TAG";
    private FragmentManager fragmentManager;
    private Double number1 = null;
    private Double number2 = null;
    private String symbol = null;
    private Calc calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            replaceFragment(new FirstFragment(), false, "First");
            addButton(1);
        }
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    private void addButton(int num) {
        Button myButton = new Button(this);
        myButton.setText(String.valueOf(num));

        LinearLayout ll = (LinearLayout) findViewById(R.id.button_layout);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }

    @Override
    public void startSecondFragment(Double number) {
        this.number1 = number;
        addButton(2);
        replaceFragment(new SecondFragment(), true, "Second");
    }

    @Override
    public void startThirdFragment(Double number) {
        this.number2 = number;
        addButton(3);
        replaceFragment(new ThirdFragment(), true, "Third");
    }

    @Override
    public void startFourthFragment(String symbol) {
        calc = new Calc(number1, number2, symbol);
        addButton(4);
        replaceFragment(FourthFragment.newInstance(calc.getExpression()), true, "Fourth");
    }
}
