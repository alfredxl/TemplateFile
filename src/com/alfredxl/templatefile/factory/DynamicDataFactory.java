package com.alfredxl.templatefile.factory;

import com.alfredxl.templatefile.bean.Template;
import com.intellij.ide.util.PropertiesComponent;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DynamicDataFactory {
    private static final String DYNAMIC_DATA = "com.alfredxl.templatefile.factory.dynamic.data";
    private static final String TEMPLATE_DATA = "com.alfredxl.templatefile.factory.template.data";


    public static Vector<String> getTitle(boolean showFormatCode) {
        Vector<String> title = new Vector<>();
        title.add("isEnabled");
        title.add("key");
        if (showFormatCode) {
            title.add("value");
        }
        return title;
    }

    public static Vector<String> getClassTitle(boolean showFormatCode) {
        Vector<String> title = new Vector<>();
        title.add("isEnabled");
        title.add("className");
        title.add("classPath");
        if (showFormatCode) {
            title.add("classNameFormat");
            title.add("classPathFormat");
        }
        return title;
    }


    public static List<Template> getDynamicData() {
        String jsonData = PropertiesComponent.getInstance().getValue(DYNAMIC_DATA, "{}");
        return getFormatBean(jsonData, false, false);
    }

    public static void setDynamicData(List<Template> templates) {
        PropertiesComponent.getInstance().setValue(DYNAMIC_DATA, setFormatJson(templates, false, false));
    }

    public static List<Template> getTemplateData() {
        String jsonData = PropertiesComponent.getInstance().getValue(TEMPLATE_DATA, "{}");
        return getFormatBean(jsonData, true, true);
    }

    public static void setTemplateData(List<Template> list) {
        PropertiesComponent.getInstance().setValue(TEMPLATE_DATA, setFormatJson(list, true, true));
    }


    private static List<Template> getFormatBean(String jsonData, boolean hasValue, boolean hasData) {
        List<Template> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    if (item != null) {
                        boolean isEnabled = item.getBoolean("isEnabled");
                        String key = item.getString("key");
                        String value = hasValue ? item.getString("value") : "";
                        String data = hasData ? item.optString("data") : "";
                        if (checkString(key) && (!hasValue || checkString(value))) {
                            list.add(new Template(isEnabled, key, value, data));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String setFormatJson(List<Template> list, boolean hasValue, boolean hasData) {
        JSONObject jsonObject = new JSONObject();
        if (list != null && list.size() > 0) {
            try {
                JSONArray jsonArray = new JSONArray();
                for (Template template : list) {
                    JSONObject jsonObjectItem = new JSONObject();
                    jsonObjectItem.put("isEnabled", template.isEnabled());
                    jsonObjectItem.put("key", template.getKey());
                    jsonObjectItem.put("value", hasValue ? template.getValue() : "");
                    jsonObjectItem.put("data", hasData ? template.getData() : "");
                    jsonArray.put(jsonObjectItem);
                }
                jsonObject.put("list", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }


    private static boolean checkString(String value) {
        return value != null && value.trim().length() > 0;
    }
}
