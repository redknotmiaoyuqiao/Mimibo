package com.redknot.mimibo.domain;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class User {
    private String idstr;
    private String screen_name;
    private String name;
    private String province;
    private String city;
    private String location;
    private String avatar_large;
    private String avatar_hd;

    public User(JSONObject jo){
        try {
            this.idstr = jo.getString("idstr");
            this.screen_name = jo.getString("screen_name");
            this.name = jo.getString("name");
            this.province = jo.getString("province");
            this.city = jo.getString("city");
            this.location = jo.getString("location");
            this.avatar_large = jo.getString("avatar_large");
            this.avatar_hd = jo.getString("avatar_hd");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }
}
