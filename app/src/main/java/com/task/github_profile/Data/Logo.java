package com.task.github_profile.Data;

import com.task.github_profile.R;

import java.util.HashMap;
import java.util.Map;

public class Logo {
    private static Map<String, Integer> logo = new HashMap<String, Integer>(){{
        put("C", R.drawable.ic_c);
        put("C++", R.drawable.ic_cpp);
        put("C#", R.drawable.ic_cs);
        put("Go", R.drawable.ic_go);
        put("HTML", R.drawable.ic_html);
        put("JavaScript", R.drawable.ic_javascript);
        put("Kotlin", R.drawable.ic_kotlin);
        put("TypeScript", R.drawable.ic_typescript);
        put("Python", R.drawable.ic_python);
        put("Java", R.drawable.ic_java);
    }};

    public static Integer getLogo(String name){
        Integer result = logo.get(name);
        if(result == null){
            return R.drawable.ic_launcher_foreground;
        } else {
            return result;
        }
    }
}
