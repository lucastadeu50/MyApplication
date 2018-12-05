package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.model.Usuario;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class GravacaowavActivity extends AppCompatActivity {

    String OUTPUT_FILE;
    MediaPlayer mediaPlayer;
    Button startBtn, pararGravacaoBtn, playBtn, stopBtn,buttonPerceptiva,buttonQuantitativa;
    private ImageButton botaoVoltar;
    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravacaowav);

        OUTPUT_FILE =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "voice8K16bitmono.wav";//"/sdcard/voice8K16bitmonoteste.wav";



        botaoVoltar = findViewById(R.id.imagemBotaoVoltarId);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GravacaowavActivity.this, InicarConsultaActivity.class));
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);

        Intent intent = getIntent();
        final Usuario usuario = (Usuario) getIntent().getSerializableExtra("Editing");





        startBtn = findViewById(R.id.buttonGravar);
        PushDownAnim.setPushDownAnimTo(startBtn)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
                Toast.makeText(GravacaowavActivity.this, "Gravando...", Toast.LENGTH_SHORT).show();

            }
        });
        pararGravacaoBtn = findViewById(R.id.buttonFinalizarGravacao);
        PushDownAnim.setPushDownAnimTo(pararGravacaoBtn)
        .setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(GravacaowavActivity.this, "Gravação finalizada.", Toast.LENGTH_SHORT).show();
               stopRecording();


               File raw = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice8K16bitmono.pcm");
              File  wav = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice8K16bitmono.wav");


               try {
                   rawToWave(raw , wav);
               } catch (IOException e) {
                   e.printStackTrace();
               }
            raw.delete();
           }
       });

        playBtn = (Button) findViewById(R.id.buttonPlay);
        PushDownAnim.setPushDownAnimTo(playBtn)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording();
            }
        });

        stopBtn = (Button) findViewById(R.id.buttonStop);
        PushDownAnim.setPushDownAnimTo(stopBtn)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayback();
            }
        });

        buttonPerceptiva = findViewById(R.id.buttonAnalisePerceptiva);
        PushDownAnim.setPushDownAnimTo(buttonPerceptiva)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GravacaowavActivity.this,TestePertubacaoActivity.class);
                intent.putExtra("gravacao",OUTPUT_FILE);
                intent.putExtra("Editing", usuario);
                startActivity(intent);
            }
        });

        buttonQuantitativa=findViewById(R.id.buttonAnaliseQuantitativa);
        PushDownAnim.setPushDownAnimTo(buttonQuantitativa)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
                Intent intent2 = new Intent(GravacaowavActivity.this,AnaliseQuantitativaActivity.class);
                intent2.putExtra("Editing", usuario);

                startActivity(intent2);

            }
        });
    }



    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2; // 2 bytes in 16bit format




    private void startRecording() {

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);

        recorder.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();

    }

    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    private void writeAudioDataToFile() {
        // Write the output audio in byte

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice8K16bitmono.pcm";
        short sData[] = new short[BufferElements2Rec];



        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format

            recorder.read(sData, 0, BufferElements2Rec);
            System.out.println("Short wirting to file" + sData.toString());
            try {
                // // writes the data to file from buffer
                // // stores the voice buffer
                byte bData[] = short2byte(sData);
                os.write(bData, 0, BufferElements2Rec * BytesPerElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        // stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void rawToWave(final File rawFile, final File waveFile) throws IOException {

        byte[] rawData = new byte[(int) rawFile.length()];
        DataInputStream input = null;
        try {
            input = new DataInputStream(new FileInputStream(rawFile));
            input.read(rawData);
        } finally {
            if (input != null) {
                input.close();
            }
        }

        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new FileOutputStream(waveFile));
            // WAVE header
            // see http://ccrma.stanford.edu/courses/422/projects/WaveFormat/
            writeString(output, "RIFF"); // chunk id
            writeInt(output, 36 + rawData.length); // chunk size
            writeString(output, "WAVE"); // format
            writeString(output, "fmt "); // subchunk 1 id
            writeInt(output, 16); // subchunk 1 size
            writeShort(output, (short) 1); // audio format (1 = PCM)
            writeShort(output, (short) 1); // number of channels
            writeInt(output, RECORDER_SAMPLERATE); // sample rate
            writeInt(output, RECORDER_SAMPLERATE * 2); // byte rate
            writeShort(output, (short) 2); // block align
            writeShort(output, (short) 16); // bits per sample
            writeString(output, "data"); // subchunk 2 id
            writeInt(output, rawData.length); // subchunk 2 size
            writeInt(output, rawData.length); // subchunk 2 size
            output.write(rawData);


        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    private void writeInt(final DataOutputStream output, final int value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }

    private void writeShort(final DataOutputStream output, final short value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
    }

    private void writeString(final DataOutputStream output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
        }
    }

    private void playRecording()  {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(OUTPUT_FILE);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        //Toast.makeText(GravacaowavActivity.this, OUTPUT_FILE, Toast.LENGTH_SHORT).show();

    }
    private void stopPlayback() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void upload(){


        FTPClient con = null;

        try
        {
            con = new FTPClient();
            con.connect("192.168.2.15",21);


            if (con.login("Lucas", "lucas"))
            {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                String data = OUTPUT_FILE;

                FileInputStream in = new FileInputStream(new File(data));
                boolean result = con.storeFile("voice8K16bitmono.wav", in);
           //     con.doCommand("chmod 777 voice8K16bitmono.wav", null );
                in.close();
                if (result) Log.v("upload result", "succeeded");
                con.logout();
                con.disconnect();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Falha ao carregar", Toast.LENGTH_SHORT).show();
        }

    }

}
