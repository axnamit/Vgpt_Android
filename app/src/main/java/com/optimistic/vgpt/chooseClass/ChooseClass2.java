package com.optimistic.vgpt.chooseClass;

import android.os.Bundle;
import android.view.View;

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
import com.optimistic.vgpt.base_class_menu.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ChooseClass2 extends BaseActivity {
    Service service;
    KProgressHUD hud;
   // Button classElven,classTwelve;
    List<Datum> responseList=new ArrayList<>();
    RecyclerView recyclerView;
    public ChooseClassAdapter chooseClassAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView=findViewById(R.id.recyclerView);

        service=new RetrofitSdk.Builder().build(this).getService();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        hud=new KProgressHUD(ChooseClass2.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        callApi();




    }
    public void callApi(){

        service.chossClasses().enqueue(new Callback<ChooseClassPojo>() {
            @Override
            public void onResponse(Call<ChooseClassPojo> call, Response<ChooseClassPojo> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if (response.body().getData()!=null){
                            hud.dismiss();
                                responseList=response.body().getData();
                                chooseClassAdapter = new ChooseClassAdapter(responseList,ChooseClass2.this);
                                recyclerView.setAdapter(chooseClassAdapter);
                                chooseClassAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChooseClassPojo> call, Throwable t) {

            }
        });


    }
   
}
