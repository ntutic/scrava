package ca.tutic.scrava.spiders;

import ca.tutic.scrava.http.Response;
import ca.tutic.scrava.http.Request;

public class Rules {
    public Request follow(Response response) {
        String nextUrl = "";
        // Process rules
        return response.get(nextUrl);
    }
}
