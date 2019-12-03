package com.optimistic.vgpt.ChooseSubjects;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseSubjectsActivity extends AppCompatActivity {
    String id;
    Service service;
    RecyclerView recyclerView;
    ChooseSubjectAdapter chooseSubjectAdapter;
    List<Datum> data = new ArrayList<>();
    private KProgressHUD KHD;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subjects);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        recyclerView = findViewById(R.id.chooseRv);
        service = new RetrofitSdk.Builder().build(this).getService();
        id = String.valueOf(Singleton.getInstance().getClassId());
        KHD = new KProgressHUD(ChooseSubjectsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //Bundle extras = getIntent().getExtras();
        /*if (extras != null) {
             id = getIntent().getExtras().getString("id");
            Log.d( "onCreate: ",id);
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }*/
        //recyclerView=findViewById(R.id.chooseRv);

        // service=new RetrofitSdk.Builder().build(this).getService();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiCall();


    }

    public void apiCall() {
        HashMap<String, String> class_id = new HashMap<>();
        class_id.put("class_id", id);

        service.chooseSubject(class_id).enqueue(new Callback<ChooseSubjectsPojo>() {
            @Override
            public void onResponse(Call<ChooseSubjectsPojo> call, Response<ChooseSubjectsPojo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData() != null) {
                            KHD.dismiss();
                            data = response.body().getData();
                            chooseSubjectAdapter = new ChooseSubjectAdapter(data, ChooseSubjectsActivity.this);
                            recyclerView.setAdapter(chooseSubjectAdapter);
                            chooseSubjectAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChooseSubjectsPojo> call, Throwable t) {

            }
        });
    }
}
