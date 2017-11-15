package com.ilyademidow.mdownloader.controllers.web;

import com.ilyademidow.mdownloader.models.SoundTrack;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for interaction with http://mp3vc.ru
 */
public class MP3VC {
    private static final String URL = "http://mp3vc.ru/";

    /**
     * Request page by HTTP method GET and parse it by Jsoup
     * @param searchingText It can be part of name of author or sound track
     * @return Sound track list
     * @throws IOException on error
     */
    public List<SoundTrack> searchSoundTrack(String searchingText) {
        List<SoundTrack> soundTracks = new LinkedList<>();
        try {
            String searchingURL = URL
                    + "search/"
                    + URLEncoder.encode(searchingText.replace(" ", "-"),"UTF-8")
                    + "/";

            // Parse the web-site
            Element body = Jsoup.connect(searchingURL).postDataCharset("UTF-8").ignoreHttpErrors(true).get().body();
            for (Element track : body.getElementsByClass("track")) {
                Element nameData = track.getElementsByClass("playlist-name").get(0);
                // Convert from HTML to POJO
                SoundTrack soundTrack = new SoundTrack();
                soundTrack.setTitle(getTitle(nameData));
                soundTrack.setAuthor(getAuthor(nameData));
                soundTrack.setDataURL(URL.substring(0,URL.length()-1) + getDataURL(track));
                soundTracks.add(soundTrack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundTracks;
    }

    private String getTitle(Element htmlTrackItem) {
        return htmlTrackItem.select("h2 > em").text();
    }

    private String getAuthor(Element htmlTrackItem) {
        return htmlTrackItem.select("h2 > b").text();
    }

    /**
     * URL which contains sound track stream
     * @param htmlTrackItem Jsoup element
     * @return URL
     */
    private String getDataURL(Element htmlTrackItem) {
        return htmlTrackItem.attr("data-mp3");
    }
}
