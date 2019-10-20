package com.optimistic.vgpt.utility;

public class Singleton {
    public static final Singleton ourInstance = new Singleton();
    private Integer classId;
    private Integer subjectId;
    private Integer module;
    private String medium;

    public static Singleton getInstance() {
        return ourInstance;
    }

    public Singleton() {
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setSubjectId(Integer subject) {
        this.subjectId = subject;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public Integer getModule() {
        return module;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return medium;
    }
}
