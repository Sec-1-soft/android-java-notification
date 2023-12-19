package com.sahanhus.notification;

import android.media.MediaPlayer;
import android.net.Uri;

public class PlayMp3 {

    private MediaPlayer mediaPlayer;
    boolean isPlaying = false;

    public PlayMp3() {

    }

    public void play(String mp3Url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.setDataSource(mp3Url);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.isLooping();
            isPlaying = true;
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double duration(){
        double musicTime = mediaPlayer.getCurrentPosition();
        return  musicTime;
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
