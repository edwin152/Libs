package com.edwin.androidlib.image.widget;
/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p/>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/11/15	| xuxiangyu 	| 	create the file
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.edwin.androidlib.R;
import com.edwin.androidlib.image.Image;
import com.edwin.androidlib.image.LoadListener;
import com.edwin.androidlib.image.SimpleImage;


/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/15
 */
public class SimpleImageView extends ImageView {

    private static final String TAG = SimpleImageView.class.getSimpleName();

    private SimpleImage.ImageKey key;
    // image params
    private Shape shape;
    private int placeholderResId;
    private int errorResId;
    private boolean isCrop;

    public SimpleImageView(Context context) {
        this(context, null);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        shape = Shape.RECTANGLE;
        placeholderResId = R.drawable.ic_defalut_image;
        errorResId = R.drawable.ic_defalut_image;
        setScaleType(ScaleType.FIT_XY);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.image);
            shape = Shape.valueOf(a.getInt(R.styleable.image_shape, 0));
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (RuntimeException ignore) {
            setImageURI();
        }
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     * The width and height will get the widget measure's.
     *
     * @param uri is the path of image.
     */
    public void setImageURI(String uri) {
        setImageURI(Image.parseURI(uri));
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     * The width and height will get the widget measure's.
     *
     * @param uri is the path of image.
     */
    public void setImageURI(Uri uri) {
        int height = getLayoutParams().height;
        int width = getLayoutParams().width;

        if (height <= 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && getMaxHeight() != 0)
                height = getMaxHeight();
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && getMinimumHeight() != 0)
                height = getMinimumHeight();
            else if (getHeight() != 0)
                height = getHeight();
            else if (getMeasuredHeight() != 0)
                height = getMeasuredHeight();
        if (height <= 0 || height > 5000)
            height = Image.SIZE_ORIGINAL;
        if (width <= 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && getMaxWidth() != 0)
                width = getMaxWidth();
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && getMinimumWidth() != 0)
                width = getMinimumWidth();
            else if (getWidth() != 0)
                width = getWidth();
            else if (getMeasuredWidth() != 0)
                width = getMeasuredWidth();
        if (width <= 0 || width > 5000)
            width = Image.SIZE_ORIGINAL;
        final SimpleImage.ImageKey key = SimpleImage.ImageKey.get(uri, width, height);
        if (checkKey(key))
            return;
        this.key = key;
        Bitmap bitmap = SimpleImage.getFromCache(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            build(bitmap);
            return;
        }

        isCrop = true;
        setPlaceHolder();
        setImageURI();
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     *
     * @param uri     is the path of image.
     * @param maxSize is the size of image.
     */
    public void setImageURI(String uri, int maxSize) {
        setImageURI(Image.parseURI(uri), maxSize);
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     *
     * @param uri     is the path of image.
     * @param maxSize is the size of image.
     */
    public void setImageURI(Uri uri, int maxSize) {
        final SimpleImage.ImageKey key = SimpleImage.ImageKey.get(uri, maxSize, maxSize);
        if (checkKey(key))
            return;
        this.key = key;
        Bitmap bitmap = SimpleImage.getFromCache(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            build(bitmap);
            return;
        }
        isCrop = false;
        setPlaceHolder();
        setImageURI();
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     *
     * @param uri    is the path of image.
     * @param width  is the size of image.
     * @param height is the size of image.
     */
    public void setImageURI(String uri, int width, int height) {
        setImageURI(Image.parseURI(uri), width, height);
    }

    /**
     * SimpleImage will be set by uri.
     * If you set a wrong uri or null, it will show the error image.
     *
     * @param uri    is the path of image.
     * @param width  is the size of image.
     * @param height is the size of image.
     */
    public void setImageURI(final Uri uri, final int width, final int height) {
        final SimpleImage.ImageKey key = SimpleImage.ImageKey.get(uri, width, height);
        if (checkKey(key))
            return;
        this.key = key;
        Bitmap bitmap = SimpleImage.getFromCache(key);
        if (bitmap != null && !bitmap.isRecycled()) {
            build(bitmap);
            return;
        }
        isCrop = true;
        setPlaceHolder();
        setImageURI();
    }

    /**
     * If key is not equal to {@link #key}, it will not set the bitmap to the image.
     *
     * @param bitmap is the resource which will be set into image.
     */
    protected void build(Bitmap bitmap) {
        if (bitmap == null && errorResId == 0)
            return;
        if (bitmap == null)
            bitmap = BitmapFactory.decodeResource(getResources(), errorResId);
        Drawable d = getDrawable();
        if (d != null && d instanceof BitmapDrawable && bitmap == ((BitmapDrawable) d).getBitmap())
            return;

        switch (shape) {
            case RECTANGLE:
                setImageBitmap(bitmap);
                break;
            case CIRCLE:
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                drawable.setCircular(true);
                setImageDrawable(drawable);
                break;
            case SQUARE:
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int size = 0;
                Log.d(TAG, "bitmap width : " + width + ", bitmap height : " + height);
                ViewGroup.LayoutParams params = getLayoutParams();
                if (width == 0 || width >= height)
                    size = height;
                else if (height == 0 || height >= width)
                    size = width;
                if (size == 0)
                    return;
                params.height = params.width = size;
                setLayoutParams(params);
                setScaleType(ScaleType.CENTER_CROP);
                setImageBitmap(bitmap);
                break;
        }
    }

    protected void setPlaceHolder() {
        setImageResource(placeholderResId);
    }

    /**
     * Save the image in {@link SimpleImageView} to local.
     *
     * @param dirPath is the save path.
     * @param l       will callback succeed or failed after save.
     */
    public void saveToLocal(String dirPath, LoadListener<Bitmap> l) {
        SimpleImage.saveToLocal(key.getUri(), dirPath, l);
    }

    /**
     * Save the image in {@link SimpleImageView} to local.
     * This method must not run in the main thread.
     *
     * @return a bitmap which is saved.
     */
    public Bitmap saveToLocalSync(String dirPath) {
        return SimpleImage.saveToLocalSync(key.getUri(), dirPath);
    }

    public int getPlaceholderResId() {
        return placeholderResId;
    }

    public void setPlaceholderResId(int placeholderResId) {
        this.placeholderResId = placeholderResId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public void setErrorResId(int errorResId) {
        this.errorResId = errorResId;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    private void setImageURI() {
        SimpleImage.getBitmap(key, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                if (checkKey(key))
                    build(bitmap);
            }

            @Override
            public void onFailed(int code) {

            }
        }, isCrop);
    }

    private boolean checkKey(SimpleImage.ImageKey newKey) {
        return key == newKey;
    }

    public enum Shape {
        RECTANGLE,
        CIRCLE,
        SQUARE;

        public static Shape valueOf(int i) {
            if (i > 0 && i < Shape.values().length)
                return Shape.values()[i];
            return RECTANGLE;
        }
    }
}
