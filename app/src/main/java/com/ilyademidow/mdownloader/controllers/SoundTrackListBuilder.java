package com.ilyademidow.mdownloader.controllers;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.ilyademidow.mdownloader.controllers.web.MP3VC;
import com.ilyademidow.mdownloader.models.SoundTrack;
import com.example.ilya.mdownloader.R;

import java.util.List;

/**
 * Request web site, parse it, fill layout
 */
public class SoundTrackListBuilder extends AsyncTask<String, Integer, String> {
    private List<SoundTrack> soundTracks;
    private Activity activity;
    LinearLayout rl = null;

    public SoundTrackListBuilder(Activity activity) {
        this.activity = activity;
        rl = (LinearLayout) activity.findViewById(R.id.scroll_layout);
    }

    /**
     * Search sound tracks
     * @param strings Unused args
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        EditText searchTextField = (EditText) activity.findViewById(R.id.search_edit_text_field);
        MP3VC mp3VC = new MP3VC();
        soundTracks = mp3VC.searchSoundTrack(searchTextField.getText().toString());

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        rl.removeAllViews();
        for (final SoundTrack soundTrack : soundTracks) {
            rl.addView(buildLayout(soundTrack));
        }
    }

    /**
     * Get layout from XML and put sound track there
     * @param soundTrack Sound track POJO
     * @return Layout
     */
    private ConstraintLayout buildLayout(final SoundTrack soundTrack) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        final ConstraintLayout songLayout = (ConstraintLayout) inflater.inflate(
                R.layout.song_layout,
                null
        );
        final ProgressBar progressBar = (ProgressBar) songLayout.findViewById(R.id.progress_bar);

        // Play or pause sound by click
        songLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundTrackPlayer.init(soundTrack.getDataURL(), progressBar);
                SoundTrackPlayer.playStopSound();

                // Reset background in other Song Layouts
                for(int i=0; i<rl.getChildCount(); i++) {
                    ConstraintLayout otherSongLayout = (ConstraintLayout) rl.getChildAt(i);
                    otherSongLayout.setBackgroundColor(Color.parseColor("#fff9fd"));
                }
                // Highlight current Song Layout
                songLayout.setBackgroundColor(Color.parseColor("#ddeeff"));
            }
        });

        // Author and Title
        TextView authorTextView = (TextView) songLayout.findViewById(R.id.author);
        authorTextView.setText(soundTrack.getAuthor());
        TextView songTextView = (TextView) songLayout.findViewById(R.id.song_name);
        songTextView.setText(soundTrack.getTitle());
        ImageButton downloadBtn = (ImageButton) songLayout.findViewById(R.id.download_button);
        // Download sound
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SoundTrackDownloader(activity, soundTrack.getDataURL(), soundTrack.getAuthor() + "-" + soundTrack.getTitle() + ".mp3").execute();
            }
        });
        return songLayout;
    }
}
