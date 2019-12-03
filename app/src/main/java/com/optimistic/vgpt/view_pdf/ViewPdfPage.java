package com.optimistic.vgpt.view_pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


public class ViewPdfPage extends AppCompatActivity {
    //PDFView pdfView;
    //private String uril;

    Service service;
    private String id;
    private String mediumId;
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
                Toast.makeText(ViewPdfPage.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                // mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(ViewPdfPage.this,
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(ViewPdfPage.this, "add is closed", Toast.LENGTH_SHORT).show();
                // startGame();
            }
        });

        //mInterstitialAd.show();


        //showInterstitial();

        pdfView = findViewById(R.id.pdfView);
/*
        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);*/
        id = String.valueOf(Singleton.getInstance().getModule());
        mediumId = String.valueOf(Singleton.getInstance().getMedium());
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
        urilFecth();
        // uril = "https://doc-04-8s-docs.googleusercontent.com/docs/securesc/9rptkuji8i7vvqes07b8nj908k0vfll3/bfr9a30s9bfl0oj5t485usc84pmtbjrv/1567951200000/07736838083722035363/03801785428433479217/0B8hXbvn1ab-BQ0NHVUpYV2NNNVE?e=download";

    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            //startGame();
        }
    }

    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

        //retryButton.setVisibility(View.INVISIBLE);
        //resumeGame(GAME_LENGTH_MILLISECONDS);
    }


    private void urilFecth() {
        HashMap<String, String> files = new HashMap<>();
        files.put("module_id", id);
        files.put("lang", mediumId);

        service.fileDownload(files).enqueue(new Callback<FileDown>() {
            @Override
            public void onResponse(Call<FileDown> call, Response<FileDown> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            if (response.body().getData() != null) {
                                if (response.body().getData() != null) {
                                    uril = response.body().getData().get(0).getFilePath();
                                    new RetrivePDFStream().execute(uril);
                                    //view_file(uril);
                                    //browser.loadUrl(uril);
                                    //new RetrivePDFStream().execute(uril);
                                    hud.dismiss();
                                } else {
                                    hud.dismiss();
                                    Toast.makeText(ViewPdfPage.this, "file not found ", Toast.LENGTH_SHORT).show();
                                }


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

    /*private void view_file(String uril) {
        try {
            URL uri = new URL(uril);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse(uril);
        pdfView.fromUri(uri)
                .swipeHorizontal(true)


                // toggle night mode
                .load();
    }*/
    private class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {

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
        protected void onPostExecute(InputStream inputStream) {
            Toast.makeText(ViewPdfPage.this, "" + inputStream.toString(), Toast.LENGTH_SHORT).show();

            pdfView.fromStream(inputStream).load();
        }
    }
    /*class RetrivePDFStream extends AsyncTask<String, Void, InputStream> {

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
    }*/
/*
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        */
/**
 * Before starting background thread
 * Show Progress Bar Dialog
 *//*

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(ViewPdfPage.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        */
/**
 * Downloading file in background thread
 *//*

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream());

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.US).format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                folder = Environment.
                folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    // Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }
    }
*/


}


