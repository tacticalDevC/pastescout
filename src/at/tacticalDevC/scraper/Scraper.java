package at.tacticalDevC.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public abstract class Scraper {

    protected Connection con;
    protected int delay;
    public Scraper(String url)
    {
        this(url, 1000);
    }
    public Scraper(String url, int delay)
    {
        con = Jsoup.connect(url);
        this.delay = delay;
    }

    public abstract void scrape();
}
