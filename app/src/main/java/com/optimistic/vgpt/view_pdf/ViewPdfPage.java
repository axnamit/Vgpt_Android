package com.optimistic.vgpt.view_pdf;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.api_client.RetrofitSdk;
import com.optimistic.vgpt.api_client.Service;
import com.optimistic.vgpt.utility.Singleton;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewPdfPage extends AppCompatActivity {
    //PDFView pdfView;
    //private String uril;

    Service service;
    private String classs, subject, module_id;
    //private String mediumId;
    /*class:10
subject:25
module:52*/
    KProgressHUD hud;
    private PDFView pdfView;
    private String uril;
    //private WebView browser;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_page);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
                //Toast.makeText(ViewPdfPage.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                // mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
               /* Toast.makeText(ViewPdfPage.this,
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(ViewPdfPage.this, "add is closed", Toast.LENGTH_SHORT).show();
                // startGame();
            }
        });

        //mInterstitialAd.show();


        //showInterstitial();

        pdfView = findViewById(R.id.pdfView);
/*
        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);*/
        classs = String.valueOf(Singleton.getInstance().getClassId());
        subject = String.valueOf(Singleton.getInstance().getSubjectId());
        module_id = String.valueOf(Singleton.getInstance().getModule());
       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = getIntent().getExtras().getString("module_id");
            mediumId=getIntent().getExtras().getString("medium");
            Log.d( "onCreate: ",id);
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }*/
        hud = new KProgressHUD(ViewPdfPage.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        service = new RetrofitSdk.Builder().build(this).getService();
        // pdfView = findViewById(R.id.pdfView);
        uril = Singleton.getInstance().getFileUrl();
        new RetrivePDFStream().execute(uril);
        //urilFecth();
        // uril = "https://doc-04-8s-docs.googleusercontent.com/docs/securesc/9rptkuji8i7vvqes07b8nj908k0vfll3/bfr9a30s9bfl0oj5t485usc84pmtbjrv/1567951200000/07736838083722035363/03801785428433479217/0B8hXbvn1ab-BQ0NHVUpYV2NNNVE?e=download";

    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            // Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            //startGame();
        }
    }




    private class RetrivePDFStream extends AsyncTask<String, Integer, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(uril);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } else {
                    Toast.makeText(ViewPdfPage.this, "" + urlConnection.getErrorStream(), Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            hud.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // Toast.makeText(ViewPdfPage.this, "" + inputStream.toString(), Toast.LENGTH_SHORT).show();

            pdfView.fromStream(inputStream).load();
            hud.dismiss();

        }
    }


}


