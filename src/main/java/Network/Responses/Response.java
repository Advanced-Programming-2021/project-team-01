package Network.Responses;

import Network.Requests.Request;
import Network.ResponseType;

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

    public void handleResponse() {

    }

    @Override
    public String toString() {
        return "Response{" +
                "request=" + request +
                ", responseType=" + responseType +
                ", content='" + content + '\'' +
                ", exception=" + exception +
                '}';
    }

    public Exception getException() {
        return exception;
    }
}
