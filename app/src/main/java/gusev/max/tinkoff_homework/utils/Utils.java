package gusev.max.tinkoff_homework.utils;

import android.view.View;
import android.widget.Toast;

/**
 * Created by v on 16/10/2017.
 */

class Utils {

    static boolean isEditTextEmpty(String text){
        return text == null || text.isEmpty();
    }

    static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static void makeToast(View view){
        Toast.makeText(view.getContext(), "Вы ввели не число! Попробуйте снова.", Toast.LENGTH_SHORT).show();
    }
}
