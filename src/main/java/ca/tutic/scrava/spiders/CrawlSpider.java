package ca.tutic.scrava.spiders;

import ca.tutic.scrava.http.Response;
import ca.tutic.scrava.http.Request;
import ca.tutic.scrava.spiders.Rules;
import ca.tutic.scrava.Item;

public abstract class CrawlSpider {
    Rules rules;

    public CrawlSpider(Rules rules) {
        this.rules = rules;
    }
    public abstract Response parseStartUrl(Request request, Item item);
    public abstract Response parseNextUrl(Request request, Item item);
    public void followUrl(Response response, Item item) {
        parseNextUrl(rules.follow(response), item);
    }
}
