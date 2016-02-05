package com.redknot.mimibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.redknot.mimibo.weibo.AccessTokenKeeper;
import com.redknot.mimibo.weibo.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by miaoyuqiao on 16/2/3.
 */
public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AccessTokenKeeper accessTokenKeeper = new AccessTokenKeeper(LoginActivity.this);
        if(accessTokenKeeper.isLogin()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);

        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mSsoHandler.authorizeWeb(new AuthListener());
                mSsoHandler.authorize(new AuthListener());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private Oauth2AccessToken mAccessToken;

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                Toast.makeText(LoginActivity.this,mAccessToken.getToken(),Toast.LENGTH_LONG).show();
                AccessTokenKeeper accessTokenKeeper = new AccessTokenKeeper(LoginActivity.this);
                accessTokenKeeper.writeAccessToken(mAccessToken);

                Toast.makeText(LoginActivity.this,mAccessToken.getToken(),Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {

        }
    }
}
