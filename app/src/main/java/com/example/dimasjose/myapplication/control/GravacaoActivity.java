package com.example.dimasjose.myapplication.control;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class GravacaoActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    MediaRecorder recorder;
    String OUTPUT_FILE;
    Button startBtn, finishBtn, playBtn, stopBtn,buttonPerceptiva,buttonQuantitativa;
    double startTime, deltaTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravacao);


        Intent intent = getIntent();
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");





        startBtn = (Button) findViewById(R.id.buttonGravar);
        PushDownAnim.setPushDownAnimTo( startBtn);
        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startTime = System.currentTimeMillis();

                        try {
                            beginRecording();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                        case MotionEvent.ACTION_UP:
                        deltaTime = (System.currentTimeMillis() - startTime);
                        if (deltaTime > 1000) {
                            stopRecording();
                            Toast.makeText(getApplicationContext(), "Record " + deltaTime / 1000 + " Sec", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Dont Record " + deltaTime / 1000 + " Sec", Toast.LENGTH_LONG).show();
                        }

                        break;
                }
                return true;
            }
        });
        finishBtn = (Button) findViewById(R.id.buttonPararGravaçao);
        PushDownAnim.setPushDownAnimTo( finishBtn);
        playBtn = (Button) findViewById(R.id.buttonPlay);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording();
            }
        });
        stopBtn = (Button) findViewById(R.id.buttonStop);
        buttonPerceptiva = findViewById(R.id.buttonAnalisePerceptiva);
        buttonPerceptiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GravacaoActivity.this,TestePertubacaoActivity.class);
                intent.putExtra("gravacao",OUTPUT_FILE);
                intent.putExtra("Editing", usuario);
                startActivity(intent);
            }
        });

        buttonQuantitativa=findViewById(R.id.buttonAnaliseQuantitativa);
        buttonQuantitativa.setEnabled(true);
        buttonQuantitativa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              upload();

            }
        });
        Date createdTime = new Date();
        finishBtn.setEnabled(true);
        playBtn.setEnabled(true);
        stopBtn.setEnabled(false);



       // OUTPUT_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecorder.3gp";
        OUTPUT_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_rec.3gp";
    }


    public void upload(){


        FTPClient con = null;

        try
        {
            con = new FTPClient();
            con.connect("192.168.2.17");

            if (con.login("acoustic", "acoustic2018"))
            {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                String data = OUTPUT_FILE;

                FileInputStream in = new FileInputStream(new File(data));
                boolean result = con.storeFile("audiorecorder.3gp", in);
                in.close();
                if (result) Log.v("upload result", "succeeded");
                con.logout();
                con.disconnect();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private void playRecording()  {
         //   playBtn.setEnabled(false);
           // startBtn.setEnabled(false);
          //  finishBtn.setEnabled(false);
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
           // if (recorder != null)
                recorder.stop();
           // finishBtn.setEnabled(false);
  //          playBtn.setEnabled(true);
          //  startBtn.setEnabled(true);
//            stopBtn.setEnabled(false);


        }

        private void beginRecording() throws IOException {
            if (recorder != null)
                recorder.release();
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

            //finishBtn.setEnabled(true);
           // startBtn.setEnabled(false);
           // playBtn.setEnabled(false);
           // startBtn.setEnabled(false);
        }

        private void dichtMediaRecorder() {
            if (recorder != null)
                recorder.release();
        }
    }

