package com.alfredxl.templatefile.constant;

public class Constants {
    public static final String SETTING_PANEL_TIPS = "配置相关参数，将以字符串形式替换模板中的文本:";
    public static final String REGEX_DYNAMIC = "\\$[^=]+\\$";
    public static final String REGEX_DYNAMIC_HAS_VALUE = "\\$[^=]+\\$=[^=]+";
    public static final String TITLE_DYNAMIC = "请输入key";
    public static final String TITLE_DYNAMIC_HAS_VALUE = "请输入key及Value";
    public static final String MESSAGE_DYNAMIC = "key请使用\"$\"开始和结束(不能含有\"=\")";
    public static final String MESSAGE_DYNAMIC_HAS_VALUE = "key请使用\"$\"开始和结束(不能含有\"=\"),key与value使用\"=\"链接";
    public static final String REGEX_TEMPLATE = "[^=]+=[^=]+";
    public static final String TITLE_TEMPLATE = "请输入className和classPath";
    public static final String MESSAGE_TEMPLATE = "classType与classPath请使用\"=\"隔开";
    public static final String DYNAMIC_TITLE = "动态参数列表:";
    public static final String TEMPLATE_TITLE = "模板类列表(类名、包名可以包涵上述参数($CDS$表示当前文件路径)):";
    public static final String CODE_TIPS = "类模板代码(注：$CDP$表示当前包路径)";
}
