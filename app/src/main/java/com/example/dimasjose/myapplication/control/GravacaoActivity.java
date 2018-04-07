package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class GravacaoActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    MediaRecorder recorder;
    String OUTPUT_FILE;
    Button startBtn, finishBtn, playBtn, stopBtn,buttonPerceptiva,buttonQuantitativa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravacao);
        Handler obj = new Handler();
        startBtn = (Button) findViewById(R.id.buttonGravar);
        startBtn.setOnClickListener((View.OnClickListener) obj);
        finishBtn = (Button) findViewById(R.id.buttonPararGravaçao);
        finishBtn.setOnClickListener((View.OnClickListener) obj);
        playBtn = (Button) findViewById(R.id.buttonPlay);
        playBtn.setOnClickListener((View.OnClickListener) obj);
        stopBtn = (Button) findViewById(R.id.buttonStop);
        stopBtn.setOnClickListener((View.OnClickListener) obj);
        buttonPerceptiva = findViewById(R.id.buttonAnalisePerceptiva);
        buttonPerceptiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GravacaoActivity.this,TestePertubacaoActivity.class);
                intent.putExtra("gravacao",OUTPUT_FILE);
                startActivity(intent);
            }
        });
        buttonQuantitativa=findViewById(R.id.buttonAnaliseQuantitativa);
        buttonQuantitativa.setEnabled(false);
        Date createdTime = new Date();
        finishBtn.setEnabled(false);
        playBtn.setEnabled(false);
        stopBtn.setEnabled(false);



        //OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gp";
        OUTPUT_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_rec.3gp";
    }


    public class Handler implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonGravar:
                    try {
                        beginRecording();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.buttonPararGravaçao:
                    try {
                        stopRecording();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.buttonPlay:
                    try {
                        playRecording();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.buttonStop:
                    try {
                        stopPlayback();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

        };

        private void playRecording()  {
            playBtn.setEnabled(false);
            startBtn.setEnabled(false);
            finishBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(OUTPUT_FILE);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            Toast.makeText(GravacaoActivity.this, OUTPUT_FILE, Toast.LENGTH_SHORT).show();

        }

        private void dichtMediaPlayer() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.release();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        private void stopPlayback() {
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playBtn.setEnabled(true);
                startBtn.setEnabled(true);
                finishBtn.setEnabled(false);
                stopBtn.setEnabled(false);
            }
        }

        private void stopRecording() {
            if (recorder != null)
                recorder.stop();
            finishBtn.setEnabled(false);
            playBtn.setEnabled(true);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);


        }

        private void beginRecording() throws IOException {
            dichtMediaRecorder();
            File outFile = new File(OUTPUT_FILE);
            if (outFile.exists())
                outFile.delete();
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            recorder.setOutputFile(OUTPUT_FILE);
            recorder.prepare();
            recorder.start();

            finishBtn.setEnabled(true);
            startBtn.setEnabled(false);
            playBtn.setEnabled(false);
            startBtn.setEnabled(false);
        }

        private void dichtMediaRecorder() {
            if (recorder != null)
                recorder.release();
        }
    }
}
