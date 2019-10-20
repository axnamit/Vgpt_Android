package com.optimistic.vgpt.choose_medium;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.optimistic.vgpt.R;
import com.optimistic.vgpt.utility.Singleton;
import com.optimistic.vgpt.view_pdf.ViewPdfPage;

import java.util.List;

public class ChooseMediumAdapter extends RecyclerView.Adapter<ChooseMediumAdapter.MyViewHolder> {
    List<Data> dataList;
    Context context;


    public ChooseMediumAdapter(ChooseMedium chooseMedium, List<Data> data) {
        this.context=chooseMedium;
        this.dataList=data;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_subject,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data data=dataList.get(position);
        holder.button.setText(data.getName());
        holder.button.setOnClickListener(view -> {
            Intent intent=new Intent(context, ViewPdfPage.class);

            Singleton.getInstance().setMedium(data.getId());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.subject);
        }
    }
}
