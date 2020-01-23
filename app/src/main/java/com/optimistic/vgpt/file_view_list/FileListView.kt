package com.optimistic.vgpt.file_view_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.optimistic.vgpt.R
import com.optimistic.vgpt.api_client.RetrofitSdk
import com.optimistic.vgpt.api_client.Service
import com.optimistic.vgpt.utility.Singleton
import kotlinx.android.synthetic.main.activity_file_list_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FileListView : AppCompatActivity() {
    private var classs: String? = null
    private var subject: String? = null
    private var moduleId: String? = null
    private lateinit var service: Service
    internal var datum1: List<Datum> = ArrayList()

    private lateinit var fileListAdapter: FileListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list_view)

        classs = Singleton.getInstance().classId.toString()
        subject = Singleton.getInstance().subjectId.toString()
        moduleId = Singleton.getInstance().module.toString()
        service = RetrofitSdk.Builder().build(this).service
        selectChapter.layoutManager = LinearLayoutManager(this)
        urilFecth()


    }

    private fun urilFecth() {
        val files: HashMap<String, String> = HashMap()


        classs?.let { files.put("class", it) }
        subject?.let { files.put("subject", it) }
        moduleId?.let { files.put("module", it) }

        /*files["class"] = classs
        files["subject"] = subject
        files["module"] = moduleId*/

        service.fileDownload(files).enqueue(object : Callback<FileDown> {
            override fun onResponse(call: Call<FileDown>, response: Response<FileDown>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.status!!) {
                            if (response.body()!!.data != null) {
                                if (response.body()!!.data != null) {
                                    datum1 = response.body()!!.data

                                    fileListAdapter = FileListAdapter(datum1, this@FileListView)
                                    selectChapter.adapter = fileListAdapter
                                    fileListAdapter.notifyDataSetChanged()

                                    println(response.body())

                                }


                                /*String hh=response.body().getData().get(0).getFileUrl();
                                uril=hh.replaceAll("\\\"","");*/

                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FileDown>, t: Throwable) {

            }
        })

    }

}
