package com.redknot.mimibo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.redknot.mimibo.R;
import com.redknot.mimibo.domain.Statuses;
import com.redknot.mimibo.view.NetImageView;

import java.util.List;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class WeiboMainAdapter extends BaseAdapter {

    private List<Statuses> statusesList;
    private Context context;
    private LayoutInflater mInflater;

    public WeiboMainAdapter(List<Statuses> statusesList, Context context) {
        this.statusesList = statusesList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.statusesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.statusesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.mInflater.inflate(R.layout.listview_main_weibo, null);

        Statuses statuses = this.statusesList.get(position);

        NetImageView status_user_img = (NetImageView) view.findViewById(R.id.status_user_img);
        TextView status_user_name = (TextView) view.findViewById(R.id.status_user_name);
        TextView status_created_at = (TextView) view.findViewById(R.id.status_created_at);
        TextView status_text = (TextView) view.findViewById(R.id.status_text);

        status_user_img.setUrl(statuses.getUser().getAvatar_hd());
        status_user_name.setText(statuses.getUser().getName());
        status_created_at.setText(statuses.getCreated_at());
        status_text.setText(statuses.getText());

        LinearLayout pic_line1 = (LinearLayout) view.findViewById(R.id.pic_line1);
        LinearLayout pic_line2 = (LinearLayout) view.findViewById(R.id.pic_line2);
        LinearLayout pic_line3 = (LinearLayout) view.findViewById(R.id.pic_line3);


        pic_line1.setVisibility(View.GONE);
        pic_line3.setVisibility(View.GONE);
        pic_line3.setVisibility(View.GONE);


        for (int i = 0; i < statuses.getPic_urls().size(); i++) {
            String pic_url = statuses.getPic_urls().get(i);
            NetImageView image = new NetImageView(this.context);
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            lp.width = 240;
            lp.height = 240;
            image.setLayoutParams(lp);

            image.setUrl(pic_url);

            if (i < 3) {
                pic_line1.setVisibility(View.VISIBLE);
                pic_line1.addView(image);
            }
            if (i < 6 && i >= 3) {
                pic_line2.setVisibility(View.VISIBLE);
                pic_line2.addView(image);
            }
            if (i < 9 && i >= 6) {
                pic_line3.setVisibility(View.VISIBLE);
                pic_line3.addView(image);
            }
        }

        if (statuses.getRetweeted() != null) {
            Statuses retweeted = statuses.getRetweeted();

            TextView retweeted_text = (TextView) view.findViewById(R.id.retweeted_text);

            retweeted_text.setText(retweeted.getUser().getName() + ":" + retweeted.getText());

            LinearLayout retweeted_pic_line1 = (LinearLayout) view.findViewById(R.id.retweeted_pic_line1);
            LinearLayout retweeted_pic_line2 = (LinearLayout) view.findViewById(R.id.retweeted_pic_line2);
            LinearLayout retweeted_pic_line3 = (LinearLayout) view.findViewById(R.id.retweeted_pic_line3);

            


            retweeted_pic_line1.setVisibility(View.GONE);
            retweeted_pic_line3.setVisibility(View.GONE);
            retweeted_pic_line3.setVisibility(View.GONE);

            for (int i = 0; i < retweeted.getPic_urls().size(); i++) {
                String pic_url = retweeted.getPic_urls().get(i);
                NetImageView image = new NetImageView(this.context);
                image.setScaleType(ImageView.ScaleType.FIT_XY);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                lp.width = 240;
                lp.height = 240;
                image.setLayoutParams(lp);

                image.setUrl(pic_url);

                if (i < 3) {
                    retweeted_pic_line1.setVisibility(View.VISIBLE);
                    retweeted_pic_line1.addView(image);
                }
                if (i < 6 && i >= 3) {
                    retweeted_pic_line2.setVisibility(View.VISIBLE);
                    retweeted_pic_line2.addView(image);
                }
                if (i < 9 && i >= 6) {
                    retweeted_pic_line3.setVisibility(View.VISIBLE);
                    retweeted_pic_line3.addView(image);
                }
            }
        }else{
            view.findViewById(R.id.retweeted).setVisibility(View.GONE);
        }

        return view;
    }
}
