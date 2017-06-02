package com.edwin.androidlib.image;
/**
 * Copyright 2016 Beijing Xinwei
 * , Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/11/19	| xuxiangyu 	| 	create the file
 */


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/11/19
 */
public class BubbleDrawable extends Drawable {

    public static float DEFAULT_WIDTH = 25;
    public static float DEFAULT_HEIGHT = 25;
    public static float DEFAULT_ANGLE = 25;
    public static float DEFAULT_POSITION = 25;
    public static float DEFAULT_STROKE_WIDTH = 0;
    public static int DEFAULT_COLOR = Color.WHITE;
    public static int DEFAULT_STROKE_COLOR = Color.LTGRAY;
    public static Type DEFAULT_TYPE = Type.COLOR;
    public static Location DEFAULT_LOCATION = Location.LEFT;

    private Path path;
    private Paint paint;
    private Shader shader;
    private Builder builder;

    public BubbleDrawable(Builder builder) {
        this.builder = builder;
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(Canvas canvas) {
        initStrokePaint();
        initStrokePath();
        canvas.drawPath(path, paint);

        initPaint();
        initPath();
        canvas.drawPath(path, paint);

    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) builder.rect.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) builder.rect.height();
    }

    private void initStrokePaint() {
        paint.setColor(builder.strokeColor);
    }

    private void initStrokePath() {
        switch (builder.location) {
            case LEFT:
                path = getLeftPath(builder.rect);
                break;
            case TOP:
                path = getTopPath(builder.rect);
                break;
            case RIGHT:
                path = getRightPath(builder.rect);
                break;
            case BOTTOM:
                path = getBottomPath(builder.rect);
                break;
        }
    }

    private void initPaint() {
        switch (builder.type) {
            case COLOR:
                paint.setColor(builder.color);
                break;
            case BITMAP:
                if (builder.bitmap == null)
                    return;
                if (shader == null)
                    shader = new BitmapShader(builder.bitmap
                            , Shader.TileMode.CLAMP
                            , Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix(null);
                float scale = Math.min(
                        getIntrinsicWidth() / (float) builder.bitmap.getWidth()
                        , getIntrinsicHeight() / (float) builder.bitmap.getHeight()
                );
                matrix.postScale(scale, scale);
                matrix.postTranslate(builder.rect.left, builder.rect.top);
                shader.setLocalMatrix(matrix);
                paint.setShader(shader);
                break;
        }
    }

    private void initPath() {
        RectF rect = new RectF(builder.rect.left + builder.strokeWidth
                , builder.rect.top + builder.strokeWidth
                , builder.rect.right - builder.strokeWidth
                , builder.rect.bottom - builder.strokeWidth
        );
        switch (builder.location) {
            case LEFT:
                path = getLeftPath(rect);
                break;
            case TOP:
                path = getTopPath(rect);
                break;
            case RIGHT:
                path = getRightPath(rect);
                break;
            case BOTTOM:
                path = getBottomPath(rect);
                break;
        }
    }

    private Path getLeftPath(RectF rect) {
        Path path = new Path();
        path.moveTo(builder.width + rect.left + builder.angleTopRight
                , rect.top);
        path.lineTo(rect.width() - builder.angleTopRight
                , rect.top);
        path.arcTo(new RectF(rect.right - builder.angleTopRight
                        , rect.top
                        , rect.right
                        , builder.angleTopRight + rect.top)
                , 270, 90);
        path.lineTo(rect.right
                , rect.bottom - builder.angleBottomRight);
        path.arcTo(new RectF(rect.right - builder.angleBottomRight
                        , rect.bottom - builder.angleBottomRight
                        , rect.right
                        , rect.bottom)
                , 0, 90);
        path.lineTo(rect.left + builder.width + builder.angleBottomLeft
                , rect.bottom);
        path.arcTo(new RectF(rect.left + builder.width
                        , rect.bottom - builder.angleBottomLeft
                        , builder.angleBottomLeft + rect.left + builder.width
                        , rect.bottom)
                , 90, 90);
        path.lineTo(rect.left + builder.width
                , builder.height + builder.position);
        path.lineTo(rect.left
                , builder.position + builder.height / 2);
        path.lineTo(rect.left + builder.width
                , builder.position);
        path.lineTo(rect.left + builder.width
                , rect.top + builder.angleTopLeft);
        path.arcTo(new RectF(rect.left + builder.width
                        , rect.top
                        , builder.angleTopLeft + rect.left + builder.width
                        , builder.angleTopLeft + rect.top)
                , 180, 90);
        path.close();
        return path;
    }

    private Path getTopPath(RectF rect) {
        Path path = new Path();
        path.moveTo(rect.left + Math.min(builder.position
                , builder.angleTopRight)
                , rect.top + builder.height);
        path.lineTo(rect.left + builder.position
                , rect.top + builder.height);
        path.lineTo(rect.left + builder.width / 2 + builder.position
                , rect.top);
        path.lineTo(rect.left + builder.width + builder.position
                , rect.top + builder.height);
        path.lineTo(rect.right - builder.angleTopRight
                , rect.top + builder.height);
        path.arcTo(new RectF(rect.right - builder.angleTopRight
                        , rect.top + builder.height
                        , rect.right
                        , builder.angleTopRight + rect.top + builder.height)
                , 270, 90);
        path.lineTo(rect.right
                , rect.bottom - builder.angleBottomRight);
        path.arcTo(new RectF(rect.right - builder.angleBottomRight
                        , rect.bottom - builder.angleBottomRight
                        , rect.right
                        , rect.bottom)
                , 0, 90);
        path.lineTo(rect.left + builder.angleBottomLeft
                , rect.bottom);
        path.arcTo(new RectF(rect.left
                        , rect.bottom - builder.angleBottomLeft
                        , builder.angleBottomLeft + rect.left
                        , rect.bottom)
                , 90, 90);
        path.lineTo(rect.left
                , rect.top + builder.height + builder.angleTopLeft);
        path.arcTo(new RectF(rect.left
                        , rect.top + builder.height
                        , builder.angleTopLeft + rect.left
                        , builder.angleTopLeft + rect.top + builder.height)
                , 180, 90);
        path.close();
        return path;
    }

    private Path getRightPath(RectF rect) {
        Path path = new Path();
        path.moveTo(rect.left + builder.angleTopRight
                , rect.top);
        path.lineTo(rect.width() - builder.angleTopRight - builder.width
                , rect.top);
        path.arcTo(new RectF(rect.right - builder.angleTopRight - builder.width
                        , rect.top
                        , rect.right - builder.width
                        , builder.angleTopRight + rect.top)
                , 270, 90);
        path.lineTo(rect.right - builder.width
                , builder.position);
        path.lineTo(rect.right
                , builder.position + builder.height / 2);
        path.lineTo(rect.right - builder.width
                , builder.position + builder.height);
        path.lineTo(rect.right - builder.width
                , rect.bottom - builder.angleBottomRight);
        path.arcTo(new RectF(rect.right - builder.angleBottomRight - builder.width
                        , rect.bottom - builder.angleBottomRight
                        , rect.right - builder.width
                        , rect.bottom)
                , 0, 90);
        path.lineTo(rect.left + builder.width
                , rect.bottom);
        path.arcTo(new RectF(rect.left
                        , rect.bottom - builder.angleBottomLeft
                        , builder.angleBottomLeft + rect.left
                        , rect.bottom)
                , 90, 90);
        path.arcTo(new RectF(rect.left
                        , rect.top
                        , builder.angleTopLeft
                        + rect.left
                        , builder.angleTopLeft + rect.top)
                , 180, 90);
        path.close();
        return path;
    }

    private Path getBottomPath(RectF rect) {
        Path path = new Path();
        path.moveTo(rect.left + builder.angleTopRight
                , rect.top);
        path.lineTo(rect.width() - builder.angleTopRight
                , rect.top);
        path.arcTo(new RectF(rect.right - builder.angleTopRight
                        , rect.top
                        , rect.right
                        , builder.angleTopRight + rect.top)
                , 270, 90);
        path.lineTo(rect.right
                , rect.bottom - builder.height - builder.angleBottomRight);
        path.arcTo(new RectF(rect.right - builder.angleBottomRight
                        , rect.bottom - builder.angleBottomRight - builder.height
                        , rect.right
                        , rect.bottom - builder.height)
                , 0, 90);
        path.lineTo(rect.left + builder.width + builder.position
                , rect.bottom - builder.height);
        path.lineTo(rect.left + builder.position + builder.width / 2
                , rect.bottom);
        path.lineTo(rect.left + builder.position
                , rect.bottom - builder.height);
        path.lineTo(rect.left + Math.min(builder.angleBottomLeft
                , builder.position)
                , rect.bottom - builder.height);
        path.arcTo(new RectF(rect.left
                        , rect.bottom - builder.angleBottomLeft - builder.height
                        , builder.angleBottomLeft + rect.left
                        , rect.bottom - builder.height)
                , 90, 90);
        path.lineTo(rect.left
                , rect.top + builder.angleTopLeft);
        path.arcTo(new RectF(rect.left
                        , rect.top
                        , builder.angleTopLeft + rect.left
                        , builder.angleTopLeft + rect.top)
                , 180, 90);
        path.close();
        return path;
    }

    public static class Builder {
        private float width = DEFAULT_WIDTH;
        private float height = DEFAULT_HEIGHT;
        private float angleTopLeft = DEFAULT_ANGLE;
        private float angleTopRight = DEFAULT_ANGLE;
        private float angleBottomLeft = DEFAULT_ANGLE;
        private float angleBottomRight = DEFAULT_ANGLE;
        private float position = DEFAULT_POSITION;
        private float strokeWidth = DEFAULT_STROKE_WIDTH;
        private int color = DEFAULT_COLOR;
        private int strokeColor = DEFAULT_STROKE_COLOR;

        private Type type = DEFAULT_TYPE;
        private Location location = DEFAULT_LOCATION;
        private RectF rect;
        private Bitmap bitmap;

        public Builder() {

        }

        public Builder(@NonNull RectF rect) {
            this.rect = rect;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setAngle(float angle) {
            this.angleTopLeft = angle;
            this.angleBottomLeft = angle;
            this.angleBottomRight = angle;
            this.angleTopRight = angle;
            return this;
        }

        public Builder setAngle(float angleTopLeft, float angleTopRight, float angleBottomRight, float angleBottomLeft) {
            this.angleTopLeft = angleTopLeft;
            this.angleTopRight = angleTopRight;
            this.angleBottomRight = angleBottomRight;
            this.angleBottomLeft = angleBottomLeft;
            return this;
        }

        public Builder setPosition(float position) {
            this.position = position;
            return this;
        }

        public Builder setStrokeWidth(float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
            return this;
        }

        public BubbleDrawable build() {
            return new BubbleDrawable(this);
        }

        public BubbleDrawable build(@NonNull RectF rect) {
            this.rect = rect;
            return new BubbleDrawable(this);
        }

        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }

        public float getAngleTopLeft() {
            return angleTopLeft;
        }

        public float getAngleBottomLeft() {
            return angleBottomLeft;
        }

        public float getAngleTopRight() {
            return angleTopRight;
        }

        public float getAngleBottomRight() {
            return angleBottomRight;
        }

        public float getPosition() {
            return position;
        }

        public float getStrokeWidth() {
            return strokeWidth;
        }

        public int getColor() {
            return color;
        }

        public int getStrokeColor() {
            return strokeColor;
        }

        public Type getType() {
            return type;
        }

        public Location getLocation() {
            return location;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}
