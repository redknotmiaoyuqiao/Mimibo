package com.redknot.mimibo.weibo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class Url {
    public static final String friends_timeline = "https://api.weibo.com/2/statuses/friends_timeline.json";

    public static String parameterToString(HashMap<String,String> parameter){
        String p_string = "";

        p_string = p_string + "?";

        Iterator iter = parameter.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();

            p_string = p_string + key + "=" + val + "&";
        }

        p_string = p_string + "1=1";

        return p_string;
    }
}
