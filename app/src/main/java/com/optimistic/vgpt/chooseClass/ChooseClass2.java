package com.optimistic.vgpt.chooseClass;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class2);
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
