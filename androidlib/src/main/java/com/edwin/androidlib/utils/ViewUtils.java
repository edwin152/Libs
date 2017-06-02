package com.edwin.androidlib.utils;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/11/22	| xuxiangyu 	| 	create the file
 */


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/22
 */
public class ViewUtils extends ContextUtils {

    public static int dip2px(int dip) {
        final float scale = appContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int px2dip(int px) {
        final float scale = appContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable, int width, int height, int defaultSize) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (width > 0 && height > 0) {
                bitmap = Bitmap.createBitmap(width,
                        height, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(dip2px(defaultSize),
                        dip2px(defaultSize), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
