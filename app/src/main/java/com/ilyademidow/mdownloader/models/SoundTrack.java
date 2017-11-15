package com.ilyademidow.mdownloader.models;

/**
 * Just necessary attributes of sound track
 */
public class SoundTrack {
    private String id;
    private String author;
    private String title;
    /**
     * URL which contains sound track stream
     */
    private String dataURL;

    public SoundTrack() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }
}
