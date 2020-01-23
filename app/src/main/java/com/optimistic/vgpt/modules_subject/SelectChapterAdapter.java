package com.optimistic.vgpt.modules_subject;

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
import com.optimistic.vgpt.file_view_list.FileListView;
import com.optimistic.vgpt.utility.Singleton;
import com.optimistic.vgpt.view_pdf.ViewPdfPage;

import java.util.List;

public class SelectChapterAdapter extends RecyclerView.Adapter<SelectChapterAdapter.MyViewholder> {
    List<Datum> datam;
    Context context;

    public SelectChapterAdapter(List<Datum> datumList, Modules modules) {
        this.context=modules;
        this.datam=datumList;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_subject,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Datum datum=datam.get(position);
        holder.button.setText(datum.getModule());

        holder.button.setOnClickListener(View->{
            Intent intent=new Intent(context, FileListView.class);
            Singleton.getInstance().setModule(datum.getId());
            System.out.println("id"+datum.getId()+"  "+"subjectId"+datum.getSubjectId());
            //intent.putExtra("module_id",String.valueOf(datum.getId()));
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return datam.size();
    }

    public class MyViewholder  extends RecyclerView.ViewHolder{
        Button button;
        ConstraintLayout constraintLayout;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.subject);
            constraintLayout=itemView.findViewById(R.id.linearLayout2);
        }
    }
}
