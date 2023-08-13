package ca.tutic.scrava.spiders;

import ca.tutic.scrava.http.Response;
import ca.tutic.scrava.http.Request;
import ca.tutic.scrava.Item;

public abstract class BasicSpider {
    private String startUrl;

    public BasicSpider(String startUrl) {this.startUrl = startUrl;}
    public abstract Response parseStartUrl(Request request, Item item);
    public abstract Response parse(Request request, Item item);
}