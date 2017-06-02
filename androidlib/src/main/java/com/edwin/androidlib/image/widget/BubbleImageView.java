package com.edwin.androidlib.image.widget;
/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p/>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/11/19	| xuxiangyu 	| 	create the file
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.edwin.androidlib.R;
import com.edwin.androidlib.image.BubbleDrawable;
import com.edwin.androidlib.image.Location;
import com.edwin.androidlib.image.Type;
import com.edwin.androidlib.utils.ViewUtils;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/19
 */
public class BubbleImageView extends ImageView {

    private BubbleDrawable.Builder builder;

    private Bitmap bubbleBitmap;
    private BubbleDrawable bubbleDrawable;
    private Drawable sourceDrawable;

    public BubbleImageView(Context context) {
        this(context, null);
    }

    public BubbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        builder = new BubbleDrawable.Builder().setType(Type.BITMAP);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Bubble);
            int location = a.getInt(R.styleable.Bubble_bubbleLocation, BubbleDrawable.DEFAULT_LOCATION.ordinal());
            float angle = a.getDimension(R.styleable.Bubble_bubbleAngle, BubbleDrawable.DEFAULT_ANGLE);
            builder
                    .setWidth(a.getDimension(R.styleable.Bubble_bubbleWidth, BubbleDrawable.DEFAULT_WIDTH))
                    .setHeight(a.getDimension(R.styleable.Bubble_bubbleHeight, BubbleDrawable.DEFAULT_HEIGHT))
                    .setPosition(a.getDimension(R.styleable.Bubble_bubblePosition, BubbleDrawable.DEFAULT_POSITION))
                    .setStrokeColor(a.getColor(R.styleable.Bubble_strokeColor, BubbleDrawable.DEFAULT_STROKE_COLOR))
                    .setStrokeWidth(a.getDimension(R.styleable.Bubble_strokeWidth, BubbleDrawable.DEFAULT_STROKE_WIDTH))
                    .setLocation(Location.mapIntToValue(location))
                    .setAngle(
                            a.getDimension(R.styleable.Bubble_bubbleAngle_topLeft, angle),
                            a.getDimension(R.styleable.Bubble_bubbleAngle_topRight, angle),
                            a.getDimension(R.styleable.Bubble_bubbleAngle_bottomRight, angle),
                            a.getDimension(R.styleable.Bubble_bubbleAngle_bottomLeft, angle)
                    );
            a.recycle();
        }
    }

    private void set() {
        int w = getWidth();
        int h = getHeight();

        if (h <= 0 && w > 0
                && sourceDrawable != null
                && sourceDrawable.getIntrinsicWidth() >= 0)
            set(w, w / sourceDrawable.getIntrinsicWidth() * sourceDrawable.getIntrinsicHeight());
        if (w <= 0 && h > 0
                && sourceDrawable != null
                && sourceDrawable.getIntrinsicHeight() >= 0)
            set(h / sourceDrawable.getIntrinsicHeight() * sourceDrawable.getIntrinsicWidth(), h);
    }

    private void set(int w, int h) {
        set(getPaddingLeft(), getPaddingTop(), w - getPaddingLeft(), h - getPaddingTop());
    }

    private void set(int left, int top, int right, int bottom) {
        if (right <= left || bottom <= top)
            return;
        if (sourceDrawable != null)
            bubbleBitmap = getBitmapFromDrawable(sourceDrawable);
        bubbleDrawable = builder
                .setBitmap(bubbleBitmap)
                .build(new RectF(left, top, right, bottom));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if (w <= 0 && h > 0)
            setMeasuredDimension(h, h);
        if (h <= 0 && w > 0)
            setMeasuredDimension(w, w);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0)
            set(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        set();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.getSaveCount();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        if (bubbleDrawable != null)
            bubbleDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setImageResource(int resId) {
        if (resId == 0)
            throw new IllegalArgumentException("getDrawable res can not be zero");
        setImageDrawable(getContext().getResources().getDrawable(resId));
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (drawable == null)
            return;
        sourceDrawable = drawable;
        set();
        super.setImageDrawable(bubbleDrawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm == null)
            return;
        bubbleBitmap = bm;
        sourceDrawable = new BitmapDrawable(getResources(), bm);
        set();
        super.setImageDrawable(bubbleDrawable);
    }

    public float getArrowWidth() {
        return builder.getWidth();
    }

    public void setArrowWidth(float bubbleWidth) {
        builder.setWidth(bubbleWidth);
    }

    public float getArrowHeight() {
        return builder.getHeight();
    }

    public void setArrowHeight(float bubbleHeight) {
        builder.setHeight(bubbleHeight);
    }

    public float getAngleTopLeft() {
        return builder.getAngleTopLeft();
    }

    public float getAngleTopRight() {
        return builder.getAngleBottomLeft();
    }

    public float getAngleBottomLeft() {
        return builder.getAngleTopRight();
    }

    public float getAngleBottomRight() {
        return builder.getAngleBottomRight();
    }

    public void setBubbleAngle(float bubbleAngle) {
        builder.setAngle(bubbleAngle);
    }

    public void setBubbleAngle(float angleTopLeft, float angleTopRight, float angleBottomRight, float angleBottomLeft) {
        builder.setAngle(angleTopLeft, angleTopRight, angleBottomRight, angleBottomLeft);
    }

    public float getBubblePosition() {
        return builder.getPosition();
    }

    public void setBubblePosition(float bubblePosition) {
        builder.setPosition(bubblePosition);
    }

    public float getStrokeWidth() {
        return builder.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        builder.setStrokeWidth(strokeWidth);
    }

    public int getStrokeColor() {
        return builder.getStrokeColor();
    }

    public void setStrokeColor(int strokeColor) {
        builder.setStrokeColor(strokeColor);
    }

    public Location getBubbleLocation() {
        return builder.getLocation();
    }

    public void setBubbleLocation(Location bubbleLocation) {
        builder.setLocation(bubbleLocation);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        return ViewUtils.getBitmapFromDrawable(drawable, getWidth(), getWidth(), 25);
    }
}
