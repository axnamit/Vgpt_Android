package com.optimistic.vgpt.api_client;

import com.optimistic.vgpt.ChooseSubjects.ChooseSubjectsPojo;
import com.optimistic.vgpt.chooseClass.ChooseClassPojo;
import com.optimistic.vgpt.modules_subject.SelectModulesPojo;
import com.optimistic.vgpt.view_pdf.FileDown;

import retrofit2.Call;
import retrofit2.http.*;


import java.util.HashMap;

public interface Service {

    @GET("classes")
    Call<ChooseClassPojo> chossClasses();


    /* @GET("subjects/{id}")
     Call<ChooseSubjectsPojo> chooseSuject(@Path("id") String id);*/
    @POST("subjects")
    Call<ChooseSubjectsPojo> chooseSubject(@QueryMap(encoded = true) HashMap<String, String> class_id);

    @POST("modules")
    Call<SelectModulesPojo> selectModulePojo(@QueryMap(encoded = true) HashMap<String, String> modulesss);

    /*@GET("modules/{id}")
    Call<SelectModulesPojo> selectModulePojo(@Path("id") String id);
*/
    /*files/11/lang/english*/


    /*
        @GET("files/{moduleId}/lang/{medium}")
    */
    @POST("files")
    Call<FileDown> fileDownload(@QueryMap(encoded = true) HashMap<String,String> files);
}
