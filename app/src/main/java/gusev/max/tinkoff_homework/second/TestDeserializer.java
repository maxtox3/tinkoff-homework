package gusev.max.tinkoff_homework.second;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by v on 07/11/2017.
 */

public class TestDeserializer implements JsonDeserializer<TestClass> {

    @Override
    public TestClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        TestClass test = new TestClass();

        if (json != null) {

            JsonObject object = json.getAsJsonObject();

            String name = object.get("name").getAsString();
            Set<Map.Entry<String, JsonElement>> set = object.get("any_map").getAsJsonObject().entrySet();
            HashMap<String, String> map = new HashMap<>();

            for (Map.Entry<String, JsonElement> entries : set) {
                map.put(entries.getKey(), entries.getValue().getAsString());
            }

            test.setName(name);
            test.setMap(map);
        }
        return test;
    }
}
