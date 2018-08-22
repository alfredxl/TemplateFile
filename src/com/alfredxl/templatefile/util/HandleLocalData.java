package com.alfredxl.templatefile.util;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.factory.DynamicDataFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

public class HandleLocalData {
    public static List<Template> getDynamicList(JSONObject jsonObject) {
        if (jsonObject != null && jsonObject.has("dynamic")) {
            try {
                JSONObject jsonObjectResult = jsonObject.getJSONObject("dynamic");
                return DynamicDataFactory.getFormatBean(jsonObjectResult.toString(), false, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Template> getTemplateList(JSONObject jsonObject) {
        if (jsonObject != null && jsonObject.has("template")) {
            try {
                JSONObject jsonObjectResult = jsonObject.getJSONObject("template");
                return DynamicDataFactory.getFormatBean(jsonObjectResult.toString(), true, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String handleDataToJson(List<Template> dynamicList, List<Template> templateList) {
        JSONObject jsonObject = new JSONObject();
        if (dynamicList != null && dynamicList.size() > 0) {
            String jsonData = DynamicDataFactory.setFormatJson(dynamicList, false, false);
            try {
                jsonObject.put("dynamic", new JSONObject(jsonData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (templateList != null && templateList.size() > 0) {
            String jsonData = DynamicDataFactory.setFormatJson(templateList, true, true);
            try {
                jsonObject.put("template", new JSONObject(jsonData));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }
}
