package at.tacticalDevC.scraper;

import at.tacticalDevC.ParseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PastebinComScraper extends Scraper {
    public PastebinComScraper(int delay) {
        super("https://pastebin.com/archive", delay);
    }

    @Override
    public void scrape() {
        int run = 0;
        while (true)
        {
            System.out.println("Run: " + run);

            Document doc = null;
            try {
                doc = con.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements elements = doc.body()
                    .getElementsByClass("maintable").first()
                    .getElementsByTag("a");

            ArrayList<String> links = new ArrayList<>();
            for (Element e : elements)
            {
                for (Attribute a : e.attributes())
                {
                    if (a.getKey().equals("href") && !a.getValue().contains("archive"))
                        links.add(a.getValue());
                }
            }

            HashMap<String, Elements> creds = new HashMap<>();
            for (String link : links)
            {
                Elements paste = null;
                String raw = null;
                try {
                    paste = Jsoup.connect("https://pastebin.com" + link)
                            .get()
                            .body()
                            .getElementById("selectable")
                            .getElementsByClass("de1");
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                for (Element line : paste)
                {
                    if(ParseUtils.parse(line.text()))
                    {
                        System.out.println("Found credentials at " + link);
                        creds.put(link, paste);
                        break;
                    }
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (Map.Entry<String, Elements> e : creds.entrySet())
            {
                File f = new File("dumps/" + e.getKey());

                if(f.exists())
                    continue;
                else {
                    try {
                        f.createNewFile();
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));

                        for (Element line : e.getValue())
                        {
                            bw.write(line.text());
                            bw.newLine();
                        }

                        bw.flush();
                        bw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            run++;
        }
    }
}
