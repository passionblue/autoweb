package com.jtrend.test.informa;

import java.io.File;

import org.apache.log4j.Logger;

public class Reader {
}

/*mport de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;
import de.nava.informa.parsers.FeedParser;

public class Reader {

    public static void main(String[] args) throws Exception {

        try {
            ChannelIF channelSports = null;//FeedParser.parse(new ChannelBuilder(), new File(Constants.SPORTS_FEED));
            ItemIF itemSports = null;
            String sportsFeedTitle = null;
            // Collection itemsNational = channelNational.getItems();
            int itemCountSports = channelSports.getItems().size();
            // log.debug("itemCountSports: " + String.valueOf(itemCountSports));
            if (itemCountSports > 0) {
                int randomItemIndex = (int) ((double) itemCountSports * Math.random());
                // log.debug("randomItemIndex: " +
                // String.valueOf(randomItemIndex));
                itemSports = (ItemIF) channelSports.getItems().toArray()[randomItemIndex];
                // log.debug("H?mtade itemSports");
                sportsFeedTitle = itemSports.getTitle();
                // log.debug("Titel fr?n itemNational: " + sportsFeedTitle);
            }
            // log.debug(Constants.NATIONAL_FEED);
            // String nationalFeedName = Constants.NATIONAL_FEED;
            ChannelIF channelNational = null;//FeedParser.parse(new ChannelBuilder(), new File(Constants.NATIONAL_FEED));
            ItemIF itemNational = null;
            String nationalFeedTitle = null;
            int itemCountNational = channelNational.getItems().size();
            // log.debug("itemCountNational: " +
            // String.valueOf(itemCountNational));
            if (itemCountNational > 0) {
                int randomItemIndex = (int) ((double) itemCountNational * Math.random());
                // log.debug("randomItemIndex: " +
                // String.valueOf(randomItemIndex));
                itemNational = (ItemIF) channelNational.getItems().toArray()[randomItemIndex];
                // log.debug("H?mtade itemNational");
                nationalFeedTitle = itemNational.getTitle();
                // log.debug("Titel fr?n itemNational: " + nationalFeedTitle);
            }
            int lengthPerFeedTitle = 49;
            int sportsFeedTitleLength = sportsFeedTitle.length();
            int nationalFeedTitleLength = nationalFeedTitle.length();
            if (sportsFeedTitleLength + nationalFeedTitleLength > 2 * lengthPerFeedTitle) {
                if (sportsFeedTitleLength > lengthPerFeedTitle && nationalFeedTitleLength > lengthPerFeedTitle) {
                    // Both feed titles are longer than maximum allowed chars
                    // each, so they are chopped down
                    sportsFeedTitle = sportsFeedTitle.substring(0, sportsFeedTitle.lastIndexOf(" ", lengthPerFeedTitle)) + "...";
                    nationalFeedTitle = nationalFeedTitle.substring(0, nationalFeedTitle.lastIndexOf(" ", lengthPerFeedTitle)) + "...";
                }
                else if (sportsFeedTitleLength > lengthPerFeedTitle && nationalFeedTitleLength <= lengthPerFeedTitle) {
                    // Sport feed title is longer than 45 chars, so its chopped
                    // down
                    sportsFeedTitle = sportsFeedTitle.substring(0, sportsFeedTitle.lastIndexOf(" ", 2 * lengthPerFeedTitle - nationalFeedTitleLength)) + "...";
                }
                else {
                    // National feed title is longer than 45 chars, so its
                    // chopped down
                    nationalFeedTitle = nationalFeedTitle.substring(0, nationalFeedTitle.lastIndexOf(" ", 2 * lengthPerFeedTitle - sportsFeedTitleLength)) + "...";
                }
            }

            
            // Put in an attribute with the entire title to a tooltip
            request.setAttribute("sportnewstitle", sportsFeedTitle);
            request.setAttribute("sportnewslink", itemSports.getLink().toString());
            request.setAttribute("sportnewslongtitle", itemSports.getTitle());
            request.setAttribute("nationalnewstitle", nationalFeedTitle);
            request.setAttribute("nationalnewslink", itemNational.getLink().toString());
            request.setAttribute("nationalnewslongtitle", itemNational.getTitle());
        }
        catch (Exception mue) {
            m_logger.error("Fel vid behandling av RSS feeds, se kommande stack trace.");
            m_logger.error("", mue);
        }
    }
    
    private static Logger m_logger = Logger.getLogger(Reader.class);
}
*/