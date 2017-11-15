package com.ilyademidow.mdownloader.controllers;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

/**
 * Invoke the standard Download Manager
 */
class SoundTrackDownloader extends AsyncTask<String, Integer, String> {
    /**
     * URL for remote mp3 file
     */
    private String remoteFile;
    /**
     * Name of saved file
     */
    private String fileName;
    /**
     * Link to general activity
     */
    private Activity activity;

    /**
     * Initialize parameters
     * @param activity
     * @param remoteFile
     * @param fileName
     */
    public SoundTrackDownloader(Activity activity, String remoteFile, String fileName) {
        this.activity = activity;
        this.remoteFile = remoteFile;
        this.fileName = fileName.replace(" ", "_");
    }

    /**
     * Invoke the standard Download Manager
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        DownloadManager.Request dmRequest = new DownloadManager.Request(Uri.parse(remoteFile));
        dmRequest.setTitle(fileName);
        dmRequest.setDescription("Downloading " + fileName + "...");
        dmRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(dmRequest);
        return null;
    }
}
