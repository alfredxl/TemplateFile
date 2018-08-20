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
    private static final String DYNAMIC_DATA = "com_paisheng_dynamic_util_td_create_class_dynamic_data";
    private static final String TEMPLATE_DATA = "com_paisheng_dynamic_util_td_create_class_template_data";


    public static Vector<String> getTitle() {
        Vector<String> title = new Vector<>();
        title.add("isEnabled");
        title.add("key");
        title.add("value");
        return title;
    }

    public static Vector<String> getClassTitle() {
        Vector<String> title = new Vector<>();
        title.add("isEnabled");
        title.add("classType");
        title.add("classPath");
        return title;
    }


    public static Vector<Vector<Object>> getStaticOrDynamicData() {
        Vector<Vector<Object>> vectors = new Vector<>();
        String staticData = PropertiesComponent.getInstance().getValue(DYNAMIC_DATA, "");
        try {
            JSONObject jsonObject = new JSONObject(staticData);
            JSONArray list = jsonObject.getJSONArray("list");
            if (list != null && list.length() > 0) {
                for (int i = 0; i < list.length(); i++) {
                    JSONObject item = list.getJSONObject(i);
                    if (item != null) {
                        boolean isEnabled = item.getBoolean("isEnabled");
                        String key = item.getString("key");
                        if (checkString(key)) {
                            Vector<Object> vectorItem = new Vector<>();
                            vectorItem.add(isEnabled);
                            vectorItem.add(key);
                            vectorItem.add("");
                            vectors.add(vectorItem);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vectors;
    }

    public static void setStaticOrDynamicData(Vector<Vector<Object>> vectors) {
        if (vectors != null && vectors.size() > 0) {
            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (Vector<Object> vectorItem : vectors) {
                    JSONObject item = new JSONObject();
                    item.put("isEnabled", vectorItem.get(0));
                    item.put("key", vectorItem.get(1));
                    item.put("value", "");
                    jsonArray.put(item);
                }
                jsonObject.put("list", jsonArray);
                PropertiesComponent.getInstance().setValue(DYNAMIC_DATA, jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            PropertiesComponent.getInstance().setValue(DYNAMIC_DATA, "");
        }
    }

    public static List<Template> getTemplateData() {
        List<Template> list = new ArrayList<>();
        String staticData = PropertiesComponent.getInstance().getValue(TEMPLATE_DATA, "");
        try {
            JSONObject jsonObject = new JSONObject(staticData);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    if (item != null) {
                        boolean isOpen = item.getBoolean("isEnabled");
                        String classType = item.getString("classType");
                        String classPath = item.getString("classPath");
                        String data = item.getString("data");
                        if (data == null) {
                            data = "";
                        }
                        if (checkString(classType) && checkString(classPath)) {
                            Vector<Object> activity = new Vector<>();
                            activity.add(isOpen);
                            activity.add(classType);
                            activity.add(classPath);
                            list.add(new Template(activity, data));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setTemplateData(List<Template> list) {
        if (list != null && list.size() > 0) {
            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (Template templateItem : list) {
                    JSONObject jsonObjectItem = new JSONObject();
                    jsonObjectItem.put("isEnabled", templateItem.getValueArrays().get(0));
                    jsonObjectItem.put("classType", templateItem.getValueArrays().get(1));
                    jsonObjectItem.put("classPath", templateItem.getValueArrays().get(2));
                    jsonObjectItem.put("data", templateItem.getClassData());
                    jsonArray.put(jsonObjectItem);
                }
                jsonObject.put("list", jsonArray);
                PropertiesComponent.getInstance().setValue(TEMPLATE_DATA, jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            PropertiesComponent.getInstance().setValue(TEMPLATE_DATA, "");
        }
    }


    private static boolean checkString(String value) {
        return value != null && value.trim().length() > 0;
    }
}
