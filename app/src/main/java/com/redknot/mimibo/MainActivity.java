package com.redknot.mimibo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.redknot.mimibo.adapter.WeiboMainAdapter;
import com.redknot.mimibo.domain.Statuses;
import com.redknot.mimibo.weibo.AccessTokenKeeper;
import com.redknot.mimibo.weibo.Constants;
import com.redknot.mimibo.weibo.Url;
import com.sina.weibo.sdk.auth.AuthInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout weibo_main_refresh;
    private ListView weibo_main_listview;

    private WeiboMainAdapter weiboMainAdapter;

    private List<Statuses> statusesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statusesList = new ArrayList<>();
        weiboMainAdapter = new WeiboMainAdapter(statusesList,MainActivity.this);

        findView();
    }

    private void findView() {
        weibo_main_refresh = (SwipeRefreshLayout) findViewById(R.id.weibo_main_refresh);
        weibo_main_listview = (ListView) findViewById(R.id.weibo_main_listview);

        weibo_main_listview.setAdapter(weiboMainAdapter);
        weibo_main_refresh.setOnRefreshListener(new MyOnRefreshListener());
    }

    private class MyOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            getTimeLine(20, 1);
        }
    }

    private void getTimeLine(int count, int page) {
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);

        HashMap<String, String> parameter = new HashMap<>();

        AccessTokenKeeper accessTokenKeeper = new AccessTokenKeeper(MainActivity.this);

        parameter.put("access_token", accessTokenKeeper.getToken());
        parameter.put("count", "" + count);
        parameter.put("page", "" + page);
        parameter.put("trim_user", "0");

        Log.e("aaaa", Url.friends_timeline + Url.parameterToString(parameter));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url.friends_timeline + Url.parameterToString(parameter), new Listener(), new ErrorListener());
        mQueue.add(stringRequest);
    }

    private class Listener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            Log.e("abc", response);
            try {
                JSONObject jo = new JSONObject(response);
                JSONArray statuses_arr = jo.getJSONArray("statuses");
                for (int i = 0; i < statuses_arr.length(); i++) {
                    JSONObject statuses = statuses_arr.getJSONObject(i);
                    statusesList.add(new Statuses(statuses));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            weiboMainAdapter.notifyDataSetChanged();
            weibo_main_refresh.setRefreshing(false);
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            weiboMainAdapter.notifyDataSetChanged();
            weibo_main_refresh.setRefreshing(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
