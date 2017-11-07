package gusev.max.tinkoff_homework.second;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by v on 07/11/2017.
 */

public class TestClass {

    private String name;

    @SerializedName("any_map")
    private Map<String, String> map;

    public TestClass(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "TestClass {\n" +
                " name = " + "\"" + name + "\",\n" +
                "   anyMap = " + map + "\n" +
                "}";
    }
}
