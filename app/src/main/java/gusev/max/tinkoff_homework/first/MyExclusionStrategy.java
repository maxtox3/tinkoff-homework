package gusev.max.tinkoff_homework.first;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by v on 06/11/2017.
 */

public class MyExclusionStrategy implements ExclusionStrategy {

    private Class<?> clazz;
    private String fieldName;

    /**
     * Для того, чтобы исключить поле из сериализации, необходимо передать в String fullPathToClassField
     * package....folderOfClass.Class.field(полный путь к полю класса + само поле)
     */

    MyExclusionStrategy(String fullPathToClassField) throws SecurityException, NoSuchFieldException, ClassNotFoundException {
        this.clazz = Class.forName(fullPathToClassField.substring(0, fullPathToClassField.lastIndexOf(".")));
        this.fieldName = fullPathToClassField.substring(fullPathToClassField.lastIndexOf(".") + 1);
    }

    /**
     * Тут можно исключить целый класс из сериализации, но по заданию это не нужно
     */
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes field) {

        return (field.getDeclaringClass() == clazz && field.getName().equals(fieldName));
    }
}