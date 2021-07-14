package Network.Requests;

import com.gilecode.yagson.YaGson;

public class Request {
    protected String authToken;

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return new YaGson().toJson(this);
    }

}
