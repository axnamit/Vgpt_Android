package com.optimistic.vgpt.modules_subject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.api_client.RetrofitSdk;
import com.optimistic.vgpt.api_client.Service;
import com.optimistic.vgpt.utility.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Modules extends AppCompatActivity {
    RecyclerView recyclerView;
    SelectChapterAdapter selectChapterAdapter;
    List<Datum> datumList = new ArrayList<>();
    private Service service;
    private String class_id, subject_id, languge_id;
    KProgressHUD hud;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        service = new RetrofitSdk.Builder().build(this).getService();
        class_id = String.valueOf(Singleton.getInstance().getClassId());
        subject_id = String.valueOf(Singleton.getInstance().getSubjectId());

        languge_id = String.valueOf(Singleton.getInstance().getMedium());
        hud = new KProgressHUD(Modules.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = getIntent().getExtras().getString("subId");
            Log.d( "onCreate: ",id);
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }*/
        recyclerView = findViewById(R.id.selectChapter);
        addChapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addChapter() {
        HashMap<String, String> mudulesId = new HashMap<>();
/*class:10
subject:25
language*/
        mudulesId.put("class", class_id);
        mudulesId.put("subject", subject_id);
        mudulesId.put("language", languge_id);
        service.selectModulePojo(mudulesId).enqueue(new Callback<SelectModulesPojo>() {
            @Override
            public void onResponse(Call<SelectModulesPojo> call, Response<SelectModulesPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            hud.dismiss();
                            datumList = response.body().getData();
                            selectChapterAdapter = new SelectChapterAdapter(datumList, Modules.this);
                            recyclerView.setAdapter(selectChapterAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectModulesPojo> call, Throwable t) {

            }
        });
    }
}
