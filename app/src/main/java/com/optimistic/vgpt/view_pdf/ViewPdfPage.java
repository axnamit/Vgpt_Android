package com.optimistic.vgpt.view_pdf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.api_client.RetrofitSdk;
import com.optimistic.vgpt.api_client.Service;
import com.optimistic.vgpt.utility.Singleton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPdfPage extends AppCompatActivity {
    PDFView pdfView;
    private String uril;

    Service service;
    private String id;
    private String mediumId;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_page);
        id=String.valueOf(Singleton.getInstance().getModule());
        mediumId=String.valueOf(Singleton.getInstance().getMedium());
       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = getIntent().getExtras().getString("module_id");
            mediumId=getIntent().getExtras().getString("medium");
            Log.d( "onCreate: ",id);
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }*/
       hud=new KProgressHUD(ViewPdfPage.this)
               .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
               .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
               .setCancellable(false)
               .setAnimationSpeed(2)
               .setDimAmount(0.5f)
               .show();
        service=new RetrofitSdk.Builder().build(this).getService();
        pdfView = findViewById(R.id.pdfView);
        urilFecth();
        uril = "https://doc-04-8s-docs.googleusercontent.com/docs/securesc/9rptkuji8i7vvqes07b8nj908k0vfll3/bfr9a30s9bfl0oj5t485usc84pmtbjrv/1567951200000/07736838083722035363/03801785428433479217/0B8hXbvn1ab-BQ0NHVUpYV2NNNVE?e=download";
        new RetrivePDFStream().execute(uril);
    }

    private void urilFecth() {
        service.fileDownload(id,mediumId).enqueue(new Callback<FileDown>() {
            @Override
            public void onResponse(Call<FileDown> call, Response<FileDown> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(response.body().getSuccess()){
                            if(response.body().getData()!=null){
                                hud.dismiss();
                                /*String hh=response.body().getData().get(0).getFileUrl();
                                uril=hh.replaceAll("\\\"","");*/

                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FileDown> call, Throwable t) {

            }
        });

    }

    class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(uril);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream).load();
        }
    }


}


