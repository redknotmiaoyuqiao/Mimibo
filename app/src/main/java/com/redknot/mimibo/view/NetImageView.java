package com.redknot.mimibo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.redknot.mimibo.weibo.MD5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by miaoyuqiao on 16/2/6.
 */
public class NetImageView extends ImageView {
    private Context context;
    private static LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(100);

    private boolean isRounded = false;

    public NetImageView(Context context) {
        super(context);
        this.context = context;
        //this.setImageResource(R.drawable.user_icon);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //this.setImageResource(R.drawable.user_icon);
    }

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //this.setImageResource(R.drawable.user_icon);
    }

    public void setIsRounded(boolean isRounded){
        this.isRounded = isRounded;
    }

    public void setUrl(String url) {
        Bitmap bitmap = mCache.get(MD5.MD5(url));
        if (bitmap == null) {
            bitmap = getImage(url);
        }
        if (bitmap == null) {
            RequestQueue requestQueue = Volley.newRequestQueue(this.context);
            ImageRequest imageRequest = new ImageRequest(
                    url,
                    new Listener(url),
                    0,
                    0,
                    Bitmap.Config.ARGB_8888,
                    new ErrorListener()
            );
            requestQueue.add(imageRequest);
        }
        if (bitmap != null) {
            setImageBitmap(bitmap);
            mCache.put(MD5.MD5(url), bitmap);
        }
    }

    private Bitmap getImage(String url) {
        url = MD5.MD5(url);
        Bitmap bitmap = null;
        try {
            FileInputStream fis = this.context.openFileInput(url);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveImage(String url, Bitmap bitmap) {
        url = MD5.MD5(url);
        try {
            FileOutputStream fos = this.context.openFileOutput(url, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Listener implements Response.Listener {
        private String url;

        public Listener(String url) {
            this.url = url;
        }

        @Override
        public void onResponse(Object response) {
            Bitmap bitmap = (Bitmap) response;

            if(isRounded){
                bitmap = getRoundedCornerBitmap(bitmap);
            }

            NetImageView.this.setImageBitmap(bitmap);
            mCache.put(MD5.MD5(this.url), bitmap);
            saveImage(this.url, bitmap);
        }
    }

    private class ErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            //NetImageView.this.setImageResource(R.drawable.load_photo_fail);
            //NetImageView.this.setImageResource(R.drawable.user_icon);
        }
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        //保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //圆形，所有只用一个

        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
