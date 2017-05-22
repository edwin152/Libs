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
import android.support.v4.util.LruCache;

import com.bumptech.glide.request.target.Target;
import com.edwin.concurrent.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/15
 */
public class Image {

    public static final int SIZE_ORIGINAL = Target.SIZE_ORIGINAL;
    /**
     * If it is not set cache size, SimpleImage will use this value to establish cache in memory.
     */
    private static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);

    private static Executor threadPool;
    private static LruCache<ImageKey, Bitmap> bitmapCache;

    /**
     * Register the framework before you use it.
     * It must run in the main thread.
     * The cache size will use the default value.
     */
    public static void initialize() {
        initialize(DEFAULT_CACHE_SIZE);
    }

    /**
     * Register the framework before you use it.
     * It must run in the main thread.
     * The cache size will use the default value.
     *
     * @param cacheSize for caches that do not override {@link LruCache#sizeOf}, this is
     *                  the maximum kb of entries in the cache. For all other caches,
     *                  this is the maximum sum of the sizes of the entries in this cache.
     */
    public static void initialize(int cacheSize) {
        threadPool = ThreadPoolFactory.newTreadPool();
        bitmapCache = new LruCache<ImageKey, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(ImageKey key, Bitmap value) {
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, ImageKey key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if (evicted)
                    oldValue.recycle();
            }
        };
    }

    /**
     * This is the key of bitmap.
     * It will save the uri, width, height of bitmap.
     */
    public static class ImageKey {
        private Uri uri;
        private int width;
        private int height;
        private static List<ImageKey> keyList;
        private static ImageKey nullKey = new ImageKey(null, 0, 0);

        private ImageKey(Uri uri, int width, int height) {
            this.uri = uri;
            this.width = width;
            this.height = height;
        }

        public static ImageKey get(String uri, int width, int height) {
            return get(parseURI(uri), width, height);
        }

        public static ImageKey get(Uri uri, int width, int height) {
            if (uri == null)
                return nullKey;
            if (width <= 0)
                width = SIZE_ORIGINAL;
            if (height <= 0)
                height = SIZE_ORIGINAL;

            if (keyList == null)
                keyList = new ArrayList<>();

            for (ImageKey key : keyList)
                if (key.uri.equals(uri) && key.width == width && key.height == height)
                    return key;

            ImageKey newKey = new ImageKey(uri, width, height);
            keyList.add(0, newKey);
            return newKey;
        }

        public Uri getUri() {
            return uri;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    /**
     * Change the uri type from {@link String} to {@link Uri}
     *
     * @param uri is the {@link String} uri.
     * @return a {@link Uri} uri.
     */
    public static Uri parseURI(String uri) {
        if (uri == null)
            return null;
        if (uri.indexOf("http://") != 0 && uri.indexOf("https://") != 0 && uri.indexOf("ftp://") != 0 && uri.indexOf("android.resource://") != 0)
            uri = "file://" + uri;
        return Uri.parse(uri);
    }

    public static Executor getThreadPool() {
        return threadPool;
    }

    public static Bitmap getFromCache(ImageKey imageKey) {
        return bitmapCache.get(imageKey);
    }

    public static void saveToCache(ImageKey imageKey, Bitmap bitmap) {
        if (bitmap == null)
            return;
        if (bitmap.isRecycled())
            return;
        int size = bitmap.getByteCount();
        if (size > DEFAULT_CACHE_SIZE / 16)
            return;
        bitmapCache.put(imageKey, bitmap);
    }
}
