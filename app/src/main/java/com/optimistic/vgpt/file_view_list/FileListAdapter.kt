package com.optimistic.vgpt.file_view_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optimistic.vgpt.R
import com.optimistic.vgpt.utility.Singleton
import com.optimistic.vgpt.view_pdf.ViewPdfPage
import kotlinx.android.synthetic.main.file_list_item.view.*

class FileListAdapter(internal var datum: List<Datum>,context: Context):
    RecyclerView.Adapter<FileListAdapter.MyViewHolder>() {
    internal var context:Context = context
    var datum1:List<Datum> =datum

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.file_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return datum1.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.materialCardView.setOnClickListener {
            Singleton.getInstance().setFileUrl(datum1[position].file)
            val intent= Intent(context,ViewPdfPage::class .java)
            //intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)

        }
        holder.itemView.textView2.text=datum1[position].type

    }

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

}