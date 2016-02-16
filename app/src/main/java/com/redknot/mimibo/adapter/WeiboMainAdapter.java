package com.redknot.mimibo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.listview_main_weibo, null);
            holder = new ViewHolder();

            holder.status_user_img = (NetImageView) convertView.findViewById(R.id.status_user_img);
            holder.status_user_name = (TextView) convertView.findViewById(R.id.status_user_name);
            holder.status_created_at = (TextView) convertView.findViewById(R.id.status_created_at);
            holder.status_text = (TextView) convertView.findViewById(R.id.status_text);

            holder.pic_line1 = (LinearLayout) convertView.findViewById(R.id.pic_line1);
            holder.pic_line2 = (LinearLayout) convertView.findViewById(R.id.pic_line2);
            holder.pic_line3 = (LinearLayout) convertView.findViewById(R.id.pic_line3);

            holder.retweeted = convertView.findViewById(R.id.retweeted);

            holder.retweeted_text = (TextView) convertView.findViewById(R.id.retweeted_text);

            holder. retweeted_pic_line1 = (LinearLayout) convertView.findViewById(R.id.retweeted_pic_line1);
            holder. retweeted_pic_line2 = (LinearLayout) convertView.findViewById(R.id.retweeted_pic_line2);
            holder. retweeted_pic_line3 = (LinearLayout) convertView.findViewById(R.id.retweeted_pic_line3);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Statuses statuses = this.statusesList.get(position);

        holder.status_user_img.setIsRounded(true);
        holder.status_user_img.setUrl(statuses.getUser().getAvatar_hd());

        holder.status_user_name.setText(statuses.getUser().getName());
        holder.status_created_at.setText(statuses.getCreated_at());
        holder.status_text.setText(statuses.getText());

        holder.pic_line1.setVisibility(View.GONE);
        holder.pic_line1.removeAllViews();
        holder.pic_line2.setVisibility(View.GONE);
        holder.pic_line2.removeAllViews();
        holder.pic_line3.setVisibility(View.GONE);
        holder.pic_line3.removeAllViews();


        for (int i = 0; i < statuses.getPic_urls().size(); i++) {
            Log.e("miaoyuqiao", statuses.getPic_urls().get(i));
            String pic_url = statuses.getPic_urls().get(i);
            NetImageView image = new NetImageView(this.context);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            lp.width = 240;
            lp.height = 240;
            image.setLayoutParams(lp);

            image.setUrl(pic_url);

            if (i < 3) {
                holder.pic_line1.setVisibility(View.VISIBLE);
                holder.pic_line1.addView(image);
            }
            if (i < 6 && i >= 3) {
                holder.pic_line2.setVisibility(View.VISIBLE);
                holder.pic_line2.addView(image);
            }
            if (i < 9 && i >= 6) {
                holder.pic_line3.setVisibility(View.VISIBLE);
                holder.pic_line3.addView(image);
            }
        }

        if (statuses.getRetweeted() != null) {
            Statuses retweeted = statuses.getRetweeted();

            holder.retweeted.setVisibility(View.VISIBLE);

            holder.retweeted_text.setText(retweeted.getUser().getName() + ":" + retweeted.getText());

            holder.retweeted_pic_line1.setVisibility(View.GONE);
            holder.retweeted_pic_line1.removeAllViews();
            holder.retweeted_pic_line3.setVisibility(View.GONE);
            holder.retweeted_pic_line2.removeAllViews();
            holder.retweeted_pic_line3.setVisibility(View.GONE);
            holder.retweeted_pic_line3.removeAllViews();

            for (int i = 0; i < retweeted.getPic_urls().size(); i++) {
                String pic_url = retweeted.getPic_urls().get(i);
                NetImageView image = new NetImageView(this.context);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(5, 5, 5, 5);
                lp.width = 240;
                lp.height = 240;
                image.setLayoutParams(lp);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);

                image.setUrl(pic_url);

                if (i < 3) {
                    holder.retweeted_pic_line1.setVisibility(View.VISIBLE);
                    holder.retweeted_pic_line1.addView(image);
                }
                if (i < 6 && i >= 3) {
                    holder.retweeted_pic_line2.setVisibility(View.VISIBLE);
                    holder.retweeted_pic_line2.addView(image);
                }
                if (i < 9 && i >= 6) {
                    holder.retweeted_pic_line3.setVisibility(View.VISIBLE);
                    holder.retweeted_pic_line3.addView(image);
                }
            }
        } else {
            holder.retweeted.setVisibility(View.GONE);
        }

        return convertView;


    }

    public static class ViewHolder {
        NetImageView status_user_img;
        TextView status_user_name;
        TextView status_created_at;
        TextView status_text;

        LinearLayout pic_line1;
        LinearLayout pic_line2;
        LinearLayout pic_line3;

        View retweeted;

        TextView retweeted_text;

        LinearLayout retweeted_pic_line1;
        LinearLayout retweeted_pic_line2;
        LinearLayout retweeted_pic_line3;
    }
}
