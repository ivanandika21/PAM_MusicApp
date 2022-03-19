package com.example.musek;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity {

    TextView var_detailTitle, var_detailArtist, var_txtStart, var_txtEnd;
    SeekBar var_seekbar;
    ImageView var_detailImage, var_fr, var_prev, var_pauseplay, var_next, var_ff;
    ArrayList<DataModel> laguList;
    DataModel currentLagu;
    MediaPlayer mediaPlayer = LaguPlayer.getInstance();
    int rot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Musek);
        setContentView(R.layout.activity_detail);

        var_detailTitle = findViewById(R.id.id_detailTitle);
        var_detailArtist = findViewById(R.id.id_detailArtist);
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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextLagu();
            }
        });

        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    var_seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    var_txtStart.setText(convertToMS(mediaPlayer.getCurrentPosition()+""));

                    if (mediaPlayer.isPlaying()) {
                        var_pauseplay.setImageResource(R.drawable.ic_pause);
                        var_detailImage.setRotation(rot++);
                    } else {
                        var_pauseplay.setImageResource(R.drawable.ic_play);
                        var_detailImage.setRotation(rot);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        var_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setResourcesWithMusic() {
        currentLagu = laguList.get(LaguPlayer.currentIndex);

        var_detailTitle.setText(currentLagu.getTitle());
        var_detailArtist.setText(currentLagu.getArtist());
        var_txtEnd.setText(convertToMS(currentLagu.getDuration()));

        var_pauseplay.setOnClickListener(view -> pausePlayLagu());
        var_next.setOnClickListener(view -> nextLagu());
        var_prev.setOnClickListener(view -> prevLagu());
        var_ff.setOnClickListener(view -> ffLagu());
        var_fr.setOnClickListener(view -> frLagu());

        playLagu();
    }

    private void playLagu() {

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentLagu.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            var_seekbar.setProgress(0);
            var_seekbar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void nextLagu() {

        if (LaguPlayer.currentIndex == laguList.size()-1) {
            Toast.makeText(DetailActivity.this,"Anda sudah mencapai akhir antrian",Toast.LENGTH_SHORT).show();
            return;
        }
        LaguPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void prevLagu() {

        if (LaguPlayer.currentIndex == 0) {
            Toast.makeText(DetailActivity.this,"Anda sudah mencapai awal antrian",Toast.LENGTH_SHORT).show();
            return;
        }
        LaguPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlayLagu() {

        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    private void ffLagu() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
        }
    }

    private void frLagu() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
        }
    }

    public static String convertToMS(String duration) {
        Long ms = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(ms) % TimeUnit.MINUTES.toSeconds(1));
    }
}