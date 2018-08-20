package com.alfredxl.templatefile.bean;


public class Template {
    private boolean isEnabled;
    private String key;
    private String value;
    private String data;

    public Template() {

    }

    public Template(boolean isEnabled, String key, String value, String data) {
        this.isEnabled = isEnabled;
        this.key = key;
        this.value = value;
        this.data = data;
    }

    public Template(boolean isEnabled, String key, String value) {
        this.isEnabled = isEnabled;
        this.key = key;
        this.value = value;
    }

    public Template(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
