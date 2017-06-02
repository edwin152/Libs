package com.edwin.androidlib.image.widget;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/12/15	| xuxiangyu 	| 	create the file
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.edwin.androidlib.image.ComplexImage;
import com.edwin.androidlib.image.Image;
import com.edwin.androidlib.image.LoadListener;
import com.edwin.androidlib.image.SimpleImage;


/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/15
 */
public class ComplexImageView extends SimpleImageView {

    private Image.ImageKey fullKey;
    private Image.ImageKey thumbnailKey;

    public ComplexImageView(Context context) {
        this(context, null);
    }

    public ComplexImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setErrorResId(0);
    }

    public void setImageUri(String full, String thumbnail) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, getMeasuredWidth(), getMeasuredHeight());
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, getMeasuredWidth(), getMeasuredHeight());
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, false);
    }

    public void setImageUri(Uri full, Uri thumbnail) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, getMeasuredWidth(), getMeasuredHeight());
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, getMeasuredWidth(), getMeasuredHeight());
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, false);
    }

    public void setImageUri(String full, String thumbnail, int maxSize) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, maxSize, maxSize);
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, maxSize, maxSize);
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, false);
    }

    public void setImageUri(Uri full, Uri thumbnail, int maxSize) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, maxSize, maxSize);
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, maxSize, maxSize);
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, false);
    }

    public void setImageUri(String full, String thumbnail, int width, int height) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, width, height);
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, width, height);
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, true);
    }

    public void setImageUri(Uri full, Uri thumbnail, int width, int height) {
        final Image.ImageKey fullKey = Image.ImageKey.get(full, width, height);
        final Image.ImageKey thumbnailKey = Image.ImageKey.get(thumbnail, width, height);
        if (checkKey(fullKey, thumbnailKey))
            return;
        this.fullKey = fullKey;
        this.thumbnailKey = thumbnailKey;
        Bitmap fullBitmap = ComplexImage.getFromCache(fullKey);
        if (fullBitmap != null && !fullBitmap.isRecycled()) {
            build(fullBitmap);
            return;
        }
        Bitmap thumbnailBitmap = ComplexImage.getFromCache(thumbnailKey);
        if (thumbnailBitmap != null && !thumbnailBitmap.isRecycled()) {
            build(thumbnailBitmap);
            return;
        }
        setPlaceHolder();
        ComplexImage.getBitmap(fullKey, thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(fullKey, thumbnailKey))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, true);
    }

    private boolean checkKey(Image.ImageKey fullKey, Image.ImageKey thumbnailKey) {
        return this.fullKey == fullKey && this.thumbnailKey == thumbnailKey;
    }

    @Override
    public void saveToLocal(String dirPath, LoadListener<Bitmap> l) {
        if (fullKey != null)
            SimpleImage.saveToLocal(fullKey.getUri(), dirPath, l);
        else {
            Bitmap bitmap = null;
            Drawable drawable = getDrawable();
            if (drawable instanceof BitmapDrawable)
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            SimpleImage.saveToLocal(bitmap, dirPath, l);
        }
    }

    @Override
    public Bitmap saveToLocalSync(String dirPath) {
        return SimpleImage.saveToLocalSync(fullKey.getUri(), dirPath);
    }
}
