package com.optimistic.vgpt.choose_medium;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.optimistic.vgpt.ChooseSubjects.Language;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.utility.Singleton;

import java.util.ArrayList;
import java.util.List;

public class ChooseMedium extends AppCompatActivity {

    private String id;
    ChooseMediumAdapter chooseMediumAdapter;
    RecyclerView recyclerView;
    List<Data> data = new ArrayList<>();
    KProgressHUD kProgressHUD;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_medium);


        findViewById(R.id.imageView2).setOnClickListener(view -> {
            finish();
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        kProgressHUD = new KProgressHUD(ChooseMedium.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        /*if (extras != null) {
            id = getIntent().getExtras().getString("module_id");
            Log.d( "onCreate: ",id);
            Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        }*/
        List<Language> languages = Singleton.getInstance().getLanguage();
        recyclerView = findViewById(R.id.selectMedium);
        chooseMediumAdapter = new ChooseMediumAdapter(ChooseMedium.this, languages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseMediumAdapter);

        kProgressHUD.dismiss();
    }

   /* private void addData() {
        Data dataa=new Data("Hindi","hindi");
        Data data1=new Data("English","english");

        data.add(dataa);
        data.add(data1);
        chooseMediumAdapter.notifyDataSetChanged();
        kProgressHUD.dismiss();

    }*/

}
