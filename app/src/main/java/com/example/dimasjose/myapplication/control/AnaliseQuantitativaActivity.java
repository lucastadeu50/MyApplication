package com.example.dimasjose.myapplication.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimasjose.myapplication.R;
import com.example.dimasjose.myapplication.config.iResultadojson;
import com.example.dimasjose.myapplication.model.ResultadoJson;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnaliseQuantitativaActivity extends AppCompatActivity {
    Button buttonOK;
    TextView textViewDurMedia, textViewUnvoicedMedia, textViewPitchBreaksMedia, textViewF0Media,textViewSmoothF0Media, textViewjitterP_Media;
    TextView textViewjitter_NMedia, textViewjitterN_Media, textViewjitter_PMedia;
    TextView textViewJITTERMedia, textViewratioMedia, textViewshimmerRMSMedia ,textViewshimmerPeakMedia, textViewSNRMedia;

    TextView textViewDurStd, textViewUnvoicedStd, textViewPitchBreaksStd, textViewF0Std,textViewSmoothF0Std, textViewjitterP_Std;
    TextView textViewjitter_NStd, textViewjitterN_Std, textViewjitter_PStd;
    TextView textViewJITTERStd, textViewratioStd, textViewshimmerRMSStd ,textViewshimmerPeakStd, textViewSNRStd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_quantitativa);

        buttonOK = findViewById(R.id.buttonOk);

        textViewDurMedia = findViewById(R.id.textViewDurResultadoMedia);
        textViewUnvoicedMedia = findViewById(R.id.textViewUnvoicedResultadoMedia);
        textViewPitchBreaksMedia = findViewById(R.id.textViewPitchBreaksResultadoMedia);
        textViewF0Media = findViewById(R.id.textViewF0ResultadoMedia);
        textViewSmoothF0Media = findViewById(R.id.textViewSmoothF0ResultadoMedia);
        textViewjitterP_Media = findViewById(R.id.textViewjitterP_ResultadoMedia);
        textViewjitter_NMedia = findViewById(R.id.textViewJitter_NResultadoMedia);
        textViewjitterN_Media = findViewById(R.id.textViewJitterN_ResultadoMedia);
        textViewjitter_PMedia = findViewById(R.id.textViewJitter_PResultadoMedia);
        textViewJITTERMedia = findViewById(R.id.textViewJITTERResultadoMedia);
        textViewratioMedia = findViewById(R.id.textViewRatioResultadoMedia);
        textViewshimmerRMSMedia = findViewById(R.id.textViewshimmerRMSResultadoMedia);
        textViewshimmerPeakMedia = findViewById(R.id.textViewShimmerPeakResultadoMedia);
        textViewSNRMedia = findViewById(R.id.textViewSnrResultadoMedia);


        textViewF0Std = findViewById(R.id.textViewF0DesvioPadrao);
        textViewSmoothF0Std = findViewById(R.id.textViewSmoothF0DesvioPadrao);
        textViewjitterP_Std = findViewById(R.id.textViewjitterP_DesvioPadrao);
        textViewjitter_NStd = findViewById(R.id.textViewjitter_NDesvioPadrao);
        textViewjitterN_Std = findViewById(R.id.textViewjitterN_DesvioPadrao);
        textViewjitter_PStd = findViewById(R.id.textViewjitter_PDesvioPadrao);
        textViewJITTERStd = findViewById(R.id.textViewJITTERDesvioPadrao);
        textViewratioStd = findViewById(R.id.textViewratioDesvioPadrao);
        textViewshimmerRMSStd = findViewById(R.id.textViewShimmerRMSDesvioPadrao);
        textViewshimmerPeakStd = findViewById(R.id.textViewshimmerPeakDesvioPadrao);
        textViewSNRStd = findViewById(R.id.textViewSnrDesvioPadrao);



        URL url= null;
        try {
            url = new URL("http://192.168.2.15/saida.php");
        } catch (MalformedURLException e) {
            Toast.makeText(this, "Sem conexão com o servidor.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }




        iResultadojson iresultadojson = iResultadojson.retrofit.create(iResultadojson.class);
        final Call<ResultadoJson> call = iresultadojson.getResultado("saida");
        call.enqueue(new Callback<ResultadoJson>() {
            @Override
            public void onResponse(Call<ResultadoJson> call, Response<ResultadoJson> response) {
                int code = response.code();
                if (code == 200) {
                    ResultadoJson resultadoJson = response.body();
                    textViewDurMedia.setText(resultadoJson.dur);
                    textViewUnvoicedMedia.setText(resultadoJson.unvoiced);
                    textViewPitchBreaksMedia.setText(resultadoJson.pitchBreaks);
                    textViewF0Media.setText(resultadoJson.f0mean);
                    textViewjitter_NMedia.setText(resultadoJson.jitter_Nmean);
                    textViewjitter_PMedia.setText(resultadoJson.jitter_Pmean);
                    textViewJITTERMedia.setText(resultadoJson.jittermean);
                    textViewratioMedia.setText(resultadoJson.ratio);
                    textViewjitterN_Media.setText(resultadoJson.jitterN_mean);
                    textViewjitterP_Media.setText(resultadoJson.jitterP_mean);
                    textViewshimmerPeakMedia.setText(resultadoJson.shimmerPeakmean);
                    textViewshimmerRMSMedia.setText(resultadoJson.shimmerRMSmean);
                    textViewSmoothF0Media.setText(resultadoJson.smoothF0mean);
                    textViewSNRMedia.setText(resultadoJson.snrmean);


                    textViewF0Std.setText(resultadoJson.f0StdDvev);
                    textViewjitter_NStd.setText(resultadoJson.jitter_NStdDev);
                    textViewjitter_PStd.setText(resultadoJson.jitter_PStdDev);
                    textViewJITTERStd.setText(resultadoJson.jitterStdDev);
                    textViewratioStd.setText(resultadoJson.ratiostr);
                    textViewjitterN_Std.setText(resultadoJson.jitterN_StdDev);
                    textViewjitterP_Std.setText(resultadoJson.jitterP_StdDev);
                    textViewshimmerPeakStd.setText(resultadoJson.shimmerPeakStdDev);
                    textViewshimmerRMSStd.setText(resultadoJson.shimmerRMSStdDev);
                    textViewSmoothF0Std.setText(resultadoJson.smoothF0StdDev);
                    textViewSNRStd.setText(resultadoJson.snrStdDev);


                } else {
                    Toast.makeText(getBaseContext(), "Falha ao acessar Web Service, anote o codigo: " + String.valueOf(code),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultadoJson> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FTPClient client = new FTPClient();
                try {
                    client.connect("192.168.2.15", 21);
                    client.login( "Lucas", "lucas");
                    client.deleteFile("B10a.stt");
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AnaliseQuantitativaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
