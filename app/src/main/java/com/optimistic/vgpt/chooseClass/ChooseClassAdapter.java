package com.optimistic.vgpt.chooseClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.optimistic.vgpt.ChooseSubjects.ChooseSubjectsActivity;
import com.optimistic.vgpt.R;
import com.optimistic.vgpt.utility.Singleton;


import java.util.List;

public class ChooseClassAdapter extends RecyclerView.Adapter<ChooseClassAdapter.MyViewHolder> {
    private List<Datum> data;
    private Context context;



    public ChooseClassAdapter(List<Datum> responseList, ChooseClass2 chooseClass2Class) {
        this.data=responseList;
        this.context=chooseClass2Class;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_class,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Datum datum=data.get(position);
        holder.button.setText(datum.getClass_());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChooseSubjectsActivity.class);
                /*intent.putExtra("id",String.valueOf(datum.getId()));*/
                Singleton.getInstance().setClassId(datum.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public MyViewHolder(View view) {
            super(view);
            button=view.findViewById(R.id.btn1);


        }
    }
}
