package com.example.musek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity {

    TextView var_detailTitle, var_txtStart, var_txtEnd;
    SeekBar var_seekbar;
    ImageView var_detailImage, var_fr, var_prev, var_pauseplay, var_next, var_ff;
    ArrayList<DataModel> laguList;
    DataModel currentLagu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        var_detailTitle = findViewById(R.id.id_detailTitle);
        var_txtStart = findViewById(R.id.id_txtStart);
        var_txtEnd = findViewById(R.id.id_txtEnd);

        var_seekbar = findViewById(R.id.id_seekbar);

        var_detailImage = findViewById(R.id.id_detailImage);

        var_fr = findViewById(R.id.id_fr);
        var_prev = findViewById(R.id.id_prev);
        var_pauseplay = findViewById(R.id.id_pauseplay);
        var_next = findViewById(R.id.id_next);
        var_ff = findViewById(R.id.id_ff);

        var_detailTitle.setSelected(true);

        laguList = (ArrayList<DataModel>) getIntent().getSerializableExtra("list");

        setResourcesWithMusic();
    }

    void setResourcesWithMusic() {
        currentLagu = laguList.get(LaguPlayer.currentIndex);

        var_detailTitle.setText(currentLagu.getTitle());
        var_txtEnd.setText(convertToMS(currentLagu.getDuration()));

        var_pauseplay.setOnClickListener(view -> pausePlayLagu());
        var_next.setOnClickListener(view -> nextLagu());
        var_prev.setOnClickListener(view -> prevLagu());

        playLagu();
    }

    private void playLagu() {

    }
    private void nextLagu() {

    }
    private void prevLagu() {

    }
    private void pausePlayLagu() {

    }

    public static String convertToMS(String duration) {
        Long ms = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(ms) % TimeUnit.MINUTES.toSeconds(1));
    }
}