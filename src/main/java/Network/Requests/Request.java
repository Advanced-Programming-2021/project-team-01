package Network.Requests;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.gilecode.yagson.com.google.gson.ExclusionStrategy;
import com.gilecode.yagson.com.google.gson.FieldAttributes;

public class Request {
    protected String authToken;

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        if (this instanceof StartBattleSuccessfullyRequest) {
            String x = PRETTY_PRINT_JSON.toJson(this);
            x = x.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
            return x;
        }
        return new YaGson().toJson(this);
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
