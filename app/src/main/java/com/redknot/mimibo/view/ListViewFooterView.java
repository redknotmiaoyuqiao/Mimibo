package com.redknot.mimibo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.redknot.mimibo.R;

/**
 * Created by miaoyuqiao on 16/2/7.
 */
public class ListViewFooterView {

    private View view_loading;
    private View view_fail;

    private View view;

    public ListViewFooterView(Context context){
        this.view = LayoutInflater.from(context).inflate(R.layout.view_main_footer, null);
        view_loading = view.findViewById(R.id.footer_loading);
        view_fail = view.findViewById(R.id.footer_fail);
    }

    public View getView(){
        return this.view;
    }

    public void setLoading(){
        view_loading.setVisibility(View.VISIBLE);
        view_fail.setVisibility(View.INVISIBLE);
    }

    public void setFail(){
        view_loading.setVisibility(View.INVISIBLE);
        view_fail.setVisibility(View.VISIBLE);
    }

}
