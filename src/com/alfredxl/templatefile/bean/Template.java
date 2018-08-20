package com.alfredxl.templatefile.bean;

import java.util.Vector;

public class Template {
    private Vector<Object> valueArrays;
    private String classData;

    public Template() {

    }

    public Template(Vector<Object> valueArrays, String classData) {
        this.valueArrays = valueArrays;
        this.classData = classData;
    }

    public Vector<Object> getValueArrays() {
        return valueArrays;
    }

    public void setValueArrays(Vector<Object> valueArrays) {
        this.valueArrays = valueArrays;
    }

    public String getClassData() {
        return classData;
    }

    public void setClassData(String classData) {
        this.classData = classData;
    }
}
