package com.redknot.mimibo.weibo;

import android.content.Context;
import android.content.SharedPreferences;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class AccessTokenKeeper {

    private Context context;

    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    public AccessTokenKeeper(Context context) {
        this.context = context;
    }

    public void writeAccessToken(Oauth2AccessToken token) {
        if (null == this.context || null == token) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.commit();
    }

    public String getToken() {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        String token = pref.getString(KEY_ACCESS_TOKEN, "");
        return token;
    }

    public boolean isLogin() {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        String token = pref.getString(KEY_ACCESS_TOKEN, "");
        if (token.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        if (null == this.context) {
            return;
        }

        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
