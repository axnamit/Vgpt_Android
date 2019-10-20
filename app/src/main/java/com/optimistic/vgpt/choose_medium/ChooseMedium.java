package com.optimistic.vgpt.choose_medium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.utility.Singleton;

import java.util.ArrayList;
import java.util.List;

public class ChooseMedium extends AppCompatActivity {

    private String id;
    ChooseMediumAdapter chooseMediumAdapter;
    RecyclerView recyclerView;
    List<Data> data=new ArrayList<>();
    KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_medium);
        Bundle extras = getIntent().getExtras();
        kProgressHUD=new KProgressHUD(ChooseMedium.this)
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

        recyclerView=findViewById(R.id.selectMedium);
        chooseMediumAdapter=new ChooseMediumAdapter(ChooseMedium.this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseMediumAdapter);
        addData();


    }

    private void addData() {
        Data dataa=new Data("Hindi","hindi");
        Data data1=new Data("English","english");

        data.add(dataa);
        data.add(data1);
        chooseMediumAdapter.notifyDataSetChanged();
        kProgressHUD.dismiss();

    }

}