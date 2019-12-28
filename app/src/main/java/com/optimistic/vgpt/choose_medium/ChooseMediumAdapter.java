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

import com.optimistic.vgpt.ChooseSubjects.Language;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.modules_subject.Modules;
import com.optimistic.vgpt.utility.Singleton;
import com.optimistic.vgpt.view_pdf.ViewPdfPage;

import java.util.List;

public class ChooseMediumAdapter extends RecyclerView.Adapter<ChooseMediumAdapter.MyViewHolder> {
    List<Language> dataList;
    Context context;


    public ChooseMediumAdapter(ChooseMedium chooseMedium, List<Language> data) {
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
        Language data=dataList.get(position);
        holder.button.setText(data.getLanguageName());
        holder.button.setOnClickListener(view -> {
            Intent intent=new Intent(context, Modules.class);

            Singleton.getInstance().setMedium(data.getLanguageId());

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
