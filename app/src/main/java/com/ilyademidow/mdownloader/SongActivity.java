package com.ilyademidow.mdownloader;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import com.ilyademidow.mdownloader.controllers.SoundTrackListBuilder;

/**
 * Main activity with search field and result list
 */
public class SongActivity extends MainActivity {
    // Set required permissions
    private String[] storagePermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ask permissions
        ActivityCompat.requestPermissions(
                this,
                storagePermissions,
                1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SoundTrackListBuilder(SongActivity.this).execute();
            }
        });
    }
}
