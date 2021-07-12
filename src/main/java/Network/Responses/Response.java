package Network.Responses;

import Network.Requests.Request;
import Network.ResponseType;
import com.gilecode.yagson.YaGson;

public abstract class Response {
    protected Request request;
    protected ResponseType responseType;
    protected String content;
    protected Exception exception;

    public Response(Request request) {
        this.request = request;
    }

    public abstract void handleRequest();

    public String getContent() {
        return content;
    }

    public Request getRequest() {
        return request;
    }

    public abstract void handleResponse();

    @Override
    public String toString() {
        return new YaGson().toJson(this);
    }


    public Exception getException() {
        return exception;
    }

}
