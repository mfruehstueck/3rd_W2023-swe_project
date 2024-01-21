package at.onlyquiz.util.jsonParser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class JSON_Object {
    public String[] getMemberNames() {
        Field[] members = this.getClass().getDeclaredFields();
        List<String> out = new ArrayList<>();

        for (Field f : members) {
            out.add(f.getName());
        }

        return out.toArray(String[]::new);
    }
}
