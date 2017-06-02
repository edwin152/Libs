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
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.edwin.androidlib.R;
import com.edwin.androidlib.image.BubbleDrawable;
import com.edwin.androidlib.image.Location;
import com.edwin.androidlib.image.Type;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/19
 */
public class BubbleTextView extends TextView {

    private BubbleDrawable.Builder builder;
    private BubbleDrawable bubbleDrawable;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        builder = new BubbleDrawable.Builder().setType(Type.COLOR);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Bubble);
            int location = a.getInt(R.styleable.Bubble_bubbleLocation, BubbleDrawable.DEFAULT_LOCATION.ordinal());
            float angle = a.getDimension(R.styleable.Bubble_bubbleAngle, BubbleDrawable.DEFAULT_ANGLE);
            builder
                    .setColor(a.getColor(R.styleable.Bubble_bubbleColor, BubbleDrawable.DEFAULT_COLOR))
                    .setWidth(a.getDimension(R.styleable.Bubble_bubbleWidth, BubbleDrawable.DEFAULT_WIDTH))
                    .setHeight(a.getDimension(R.styleable.Bubble_bubbleHeight, BubbleDrawable.DEFAULT_HEIGHT))
                    .setStrokeColor(a.getColor(R.styleable.Bubble_strokeColor, BubbleDrawable.DEFAULT_STROKE_COLOR))
                    .setPosition(a.getDimension(R.styleable.Bubble_bubblePosition, BubbleDrawable.DEFAULT_POSITION))
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
        initPadding();
    }

    private void set() {
        set(getWidth(), getHeight());
    }

    private void set(int w, int h) {
        set(0, 0, w, h);
    }

    private void set(int left, int top, int right, int bottom) {
        if (right < left || bottom < top)
            return;
        bubbleDrawable = builder.build(new RectF(left, top, right, bottom));
    }

    private void initPadding() {
        switch (builder.getLocation()) {
            case LEFT:
            case RIGHT:
                setPadding(getPaddingLeft() + (int) builder.getWidth() * 2
                        , getPaddingTop()
                        , getPaddingRight() + (int) builder.getWidth() * 2
                        , getPaddingBottom()
                );
                break;
            case TOP:
            case BOTTOM:
                setPadding(getPaddingLeft()
                        , getPaddingTop() + (int) builder.getHeight() * 2
                        , getPaddingRight()
                        , getPaddingBottom() + (int) builder.getHeight() * 2
                );
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0)
            set(w, h);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        set();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bubbleDrawable != null)
            bubbleDrawable.draw(canvas);
        super.onDraw(canvas);
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

    public int getBubbleColor() {
        return builder.getColor();
    }

    public void setBubbleColor(int color) {
        builder.setColor(color);
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

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, type);
    }
}
