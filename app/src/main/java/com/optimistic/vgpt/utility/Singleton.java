package com.optimistic.vgpt.utility;

import com.optimistic.vgpt.ChooseSubjects.Language;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Singleton {
    public static final Singleton ourInstance = new Singleton();
    private String medium;
    private Integer classId;
    private Integer subjectId;
    private List<Language> language;
    private Integer module;
    private String fileUrl;


    public static Singleton getInstance() {
        return ourInstance;
    }

    public Singleton() {
    }


    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return medium;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public Integer getModule() {
        return module;
    }

    public void setFileUrl(@Nullable String file) {
        this.fileUrl=file;
    }
    public String getFileUrl(){
        return fileUrl;
    }
}
