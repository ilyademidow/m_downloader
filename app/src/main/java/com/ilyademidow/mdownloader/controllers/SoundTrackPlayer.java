package com.ilyademidow.mdownloader.controllers;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

public class SoundTrackPlayer {

    private static MediaPlayer mp = null;
    private static String url = null;

    public static void init(String targetUrl, ProgressBar progressBar) {
        if (mp == null) {
            mp = new MediaPlayer();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        if (url == null) {
            initializeMp(targetUrl, progressBar);
        }
        // Change track
        else if (!url.equals(targetUrl)) {
            destroyMp();
            initializeMp(targetUrl, progressBar);
        }
    }

    public static void playStopSound() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    private static void initializeMp(String targetUrl, final ProgressBar progressBar) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            mp.setDataSource(targetUrl);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.INVISIBLE);
                    mp.start();
                }
            });
            mp.prepareAsync();
            url = targetUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void destroyMp() {
        mp.stop();
        mp.reset();
    }
}
