package Network.Responses;

import Network.Requests.Request;
import Network.ResponseType;
import com.gilecode.yagson.YaGson;

import static Network.Requests.Request.PRETTY_PRINT_JSON;

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
        return PRETTY_PRINT_JSON.toJson(this);
    }


    public Exception getException() {
        return exception;
    }

}
