package com.redknot.mimibo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import java.net.ContentHandler;

/**
 * Created by miaoyuqiao on 16/2/7.
 */
public class MyListView extends ListView {

    private OnUpRefreshListener listener = null;

    public MyListView(Context context) {
        super(context);
        setMain(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMain(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMain(context);
    }

    private ListViewFooterView footview = null;

    private void setMain(Context context){
        this.setOnScrollListener(new MyOnScrollListener());
        this.footview = new ListViewFooterView(context);
        this.addFooterView(footview.getView());
    }

    public void setFooterLoading(){
        footview.setLoading();
    }

    public void setFooterFail(){
        footview.setFail();
    }

    public void setOnUpRefreshListener(OnUpRefreshListener listener){
        this.listener = listener;
    }

    public interface OnUpRefreshListener {
        public void onRefresh();
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (totalItemCount - 1 <= firstVisibleItem + visibleItemCount) {
                listener.onRefresh();
            }
        }
    }
}
