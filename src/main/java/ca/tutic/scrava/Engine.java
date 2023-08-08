package ca.tutic.scrava;
import java.util.Properties;
import java.util.Scanner;

import org.jsoup.Jsoup;

public class Engine {
    Properties props;

    public Engine(Properties props, Scanner scanner) {
        this.props = props;
    }

    public void runSpider(String spider) {
        System.out.println(spider);
    }
    
}
