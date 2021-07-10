package Network.Responses;

import Network.Requests.Request;
import Network.ResponseType;

public abstract class Response {
    protected Request request;
    protected ResponseType responseType;
    protected String content;

    public abstract void handleRequest();
    public abstract String getContent();

}
