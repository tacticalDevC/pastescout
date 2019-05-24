package at.tacticalDevC;

import at.tacticalDevC.scraper.PastebinComScraper;
import org.apache.commons.cli.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File dumpFolder = new File("dumps");
        if(!dumpFolder.exists())
            dumpFolder.mkdir();

        Options options = new Options()
                .addOption("1", "pastebin", false, "Scrape from pastebin.com")
                .addOption("2", "pasteorg", false, "Scrape from paste.org")
                .addOption("t", "time", true, "Specify delay between requests");

        try
        {
            CommandLine cl = new DefaultParser().parse(options, args);

            if(cl.hasOption("1"))
                new PastebinComScraper((cl.getOptionValue("t") != null ? Integer.parseInt(cl.getOptionValue("t")) : 1000)).scrape();
            else
            {
                new HelpFormatter().printHelp("java -jar pastescout.jar", options);
            }

        }
        catch (ParseException e)
        {
            System.out.println("Oops! Something went wrong... Please open an issue!");
            e.printStackTrace();
        }
    }
}
