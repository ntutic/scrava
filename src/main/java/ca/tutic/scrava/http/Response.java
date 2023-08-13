package ca.tutic.scrava.http;

import ca.tutic.scrava.http.Request;

public class Response {
    public Request get(String url) {
        return new Request(url, this);
    }
}
