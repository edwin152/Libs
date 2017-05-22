package com.edwin.androidlib.image;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/12/15	| xuxiangyu 	| 	create the file
 */


import android.graphics.Bitmap;
import android.net.Uri;

import com.edwin.androidlib.utils.HandlerUtils;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/15
 */
public class ComplexImage extends SimpleImage {

    public static void getBitmap(String full, String thumbnail, LoadListener<Bitmap> l) {
        getBitmap(parseURI(full), parseURI(thumbnail), l);
    }

    public static void getBitmap(Uri full, Uri thumbnail, LoadListener<Bitmap> l) {
        getBitmap(full, thumbnail, SIZE_ORIGINAL, SIZE_ORIGINAL, l, true);
    }

    public static void getBitmap(String full, String thumbnail, int maxSize, LoadListener<Bitmap> l) {
        getBitmap(parseURI(full), parseURI(thumbnail), maxSize, l);
    }

    public static void getBitmap(Uri full, Uri thumbnail, int maxSize, LoadListener<Bitmap> l) {
        getBitmap(ImageKey.get(full, maxSize, maxSize), ImageKey.get(thumbnail, maxSize, maxSize), l, false);
    }

    public static void getBitmap(String full, String thumbnail, int width, int height, LoadListener<Bitmap> l) {
        getBitmap(parseURI(full), parseURI(thumbnail), width, height, l, true);
    }

    public static void getBitmap(Uri full, final Uri thumbnail, int width, int height, final LoadListener<Bitmap> l, boolean isCrop) {
        getBitmap(ImageKey.get(full, width, height), ImageKey.get(thumbnail, width, height), l, isCrop);
    }

    public static void getBitmap(ImageKey fullKey, ImageKey thumbnailKey, final LoadListener<Bitmap> l, boolean isCrop) {
        final ComplexBitmap complexBitmap = new ComplexBitmap();
        getBitmap(fullKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                complexBitmap.full = bitmap;
                callbackBitmap(bitmap, l);
            }

            @Override
            public void onFailed(int code) {
                callbackFailed(code, l);
            }
        }, isCrop);
        getBitmap(thumbnailKey, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                complexBitmap.thumbnail = bitmap;
                if (complexBitmap.full == null)
                    callbackBitmap(bitmap, l);
            }

            @Override
            public void onFailed(int code) {
                callbackFailed(code, l);
            }
        }, isCrop);
    }

    private synchronized static void callbackBitmap(final Bitmap bitmap, final LoadListener<Bitmap> l) {
        if (l == null)
            return;
        HandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                l.onLoad(bitmap);
            }
        });
    }

    private synchronized static void callbackFailed(final int code, final LoadListener<Bitmap> l) {
        if (l == null) {
            return;
        }
        HandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                l.onFailed(code);
            }
        });
    }

    private static class ComplexBitmap {
        Bitmap full;
        Bitmap thumbnail;
    }
}
