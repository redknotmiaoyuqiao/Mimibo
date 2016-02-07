package com.redknot.mimibo.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class Statuses {
    private String created_at;
    private String idstr;
    private String text;
    private String source;
    private ArrayList<String> pic_urls;
    private User user;

    private Statuses retweeted = null;

    public Statuses(JSONObject jo) {

        pic_urls = new ArrayList<>();
        try {
            this.created_at = jo.getString("created_at");
            this.idstr = jo.getString("idstr");
            this.text = jo.getString("text");
            this.source = jo.getString("source");

            this.user = new User(jo.getJSONObject("user"));



            JSONArray pic_arr = jo.getJSONArray("pic_urls");
            for (int i = 0; i < pic_arr.length(); i++) {
                JSONObject pic = pic_arr.getJSONObject(i);
                pic_urls.add(pic.getString("thumbnail_pic"));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONObject jo_retweeted = jo.getJSONObject("retweeted_status");
            retweeted = new Statuses(jo_retweeted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(ArrayList<String> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Statuses getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Statuses retweeted) {
        this.retweeted = retweeted;
    }
}
