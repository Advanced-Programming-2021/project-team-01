import Network.Requests.SkipSerialisation;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.ExclusionStrategy;
import com.gilecode.yagson.com.google.gson.FieldAttributes;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String input = "{\"@type\":\"Dadbeh\",\"@val\":{\"friends\":[{\"name\":\"hello\",\"id\":123}],\"name\":\"dadbeh\",\"id\":321312,\"aghGol\":\"@root.friends.0\"}}";
        String input2 = "{" +
                "  \"@type\": \"Dadbeh\"," +
                "  \"@val\": {" +
                "    \"friends\": [" +
                "      {" +
                "        \"name\": \"hello\"" +
                "      }" +
                "    ]," +
                "    \"name\": \"dadbeh\"," +
                "    \"aghGol\": \"@root.friends.0\"" +
                "  }" +
                "}";
        Dadbeh dadbeh = new YaGson().fromJson(input2,Dadbeh.class);

    }

    public static final YaGson PRETTY_PRINT_JSON = new YaGsonBuilder()
            .addSerializationExclusionStrategy(new ExclusionStrategy()
            {
                @Override
                public boolean shouldSkipField(FieldAttributes f)
                {
                    return f.getAnnotation(SkipSerialisation.class) != null;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz)
                {
                    return false;
                }
            })
            .setPrettyPrinting()
            .create();
}
class Dadbeh {
    ArrayList<Friend> friends = new ArrayList<>();
    String name = "dadbeh";
    @SkipSerialisation
    int id;
    public Dadbeh(int id){
        this.id = id;
    }
    Friend aghGol = new Friend("hello", 123);
    {
        friends.add(aghGol);
    }
}
class Friend {
    String name;
    @SkipSerialisation
    int id;

    public Friend(String name, int id){
        this.name = name;
        this.id = id;
    }
}
