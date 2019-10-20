package com.optimistic.vgpt.ChooseSubjects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.optimistic.vgpt.R;
import com.optimistic.vgpt.modules_subject.Modules;
import com.optimistic.vgpt.utility.Singleton;

import java.util.List;

public class ChooseSubjectAdapter extends RecyclerView.Adapter<ChooseSubjectAdapter.MyViewHolder> {
    List<Datum> data;
    Context context;

    public ChooseSubjectAdapter(List<Datum> data, ChooseSubjectsActivity chooseSubjectsActivity) {
        this.context=chooseSubjectsActivity;
        this.data=data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_subject,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Datum datum=data.get(position);
        holder.button.setText(datum.getName());
        //String classId=);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Modules.class);
                Singleton.getInstance().setSubjectId(datum.getClassId());
                //intent.putExtra("subId",String.valueOf(datum.getClassId()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.subject);
            constraintLayout=itemView.findViewById(R.id.linearLayout2);

        }
    }
}
