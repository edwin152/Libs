package com.edwin.androidlib.image;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/12/14	| xuxiangyu 	| 	create the file
 */


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.edwin.androidlib.utils.ContextUtils;
import com.edwin.androidlib.utils.HandlerUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/14
 */
public class SimpleImage extends Image {

    private static final String TAG = SimpleImage.class.getSimpleName();

    /**
     * {@link #getBitmap(Uri, LoadListener)}
     */
    public static void getBitmap(String uri, LoadListener<Bitmap> l) {
        getBitmap(parseURI(uri), l);
    }

    /**
     * Get a bitmap with full width and height.
     *
     * @param uri is the path of bitmap.
     * @param l   will callback when the bitmap load in main thread.
     */
    public static void getBitmap(Uri uri, LoadListener<Bitmap> l) {
        getBitmap(uri, SIZE_ORIGINAL, SIZE_ORIGINAL, l);
    }

    /**
     * {@link #getBitmap(Uri, int, LoadListener)}
     */
    public static void getBitmap(String uri, int maxSize, LoadListener<Bitmap> l) {
        getBitmap(parseURI(uri), maxSize, l);
    }

    /**
     * Get a bitmap from memory cache, disk cache or internet.
     *
     * @param uri     is the path of bitmap.
     * @param maxSize is the size of bitmap you want to show.
     *                The more detail you set the width and height, the fast bitmap load.
     *                If you want to get the full image, i suggest use {@link #SIZE_ORIGINAL}
     *                or use {@link #getBitmapSync(Uri)}, {@link #getBitmapSync(String)}
     *                In the theory, you can use any integer number below or is zero,
     *                but it is not a prefect choice.
     * @param l       will callback when the bitmap load in main thread.
     */
    public static void getBitmap(Uri uri, int maxSize, LoadListener<Bitmap> l) {
        getBitmap(ImageKey.get(uri, maxSize, maxSize), l, false);
    }

    /**
     * {@link #getBitmap(Uri, int, int, LoadListener)}
     */
    public static void getBitmap(String uri, int width, int height, LoadListener<Bitmap> l) {
        getBitmap(parseURI(uri), width, height, l);
    }

    /**
     * Get a bitmap from memory cache, disk cache or internet.
     *
     * @param uri    is the path of bitmap.
     * @param width  is the size of bitmap you want to show.
     * @param height is the size of bitmap you want to show.
     *               The more detail you set the width and height, the fast bitmap load.
     *               If you want to get the full image, i suggest use {@link #SIZE_ORIGINAL}
     *               or use {@link #getBitmapSync(Uri)}, {@link #getBitmapSync(String)}
     *               In the theory, you can use any integer number below or is zero,
     *               but it is not a prefect choice.
     * @param l      will callback when the bitmap load in main thread.
     */
    public static void getBitmap(Uri uri, int width, int height, LoadListener<Bitmap> l) {
        getBitmap(ImageKey.get(uri, width, height), l, true);
    }

    /**
     * Get a bitmap from memory cache, disk cache or internet.
     *
     * @param key is the {@link ImageKey}.
     * @param l   will callback when the bitmap load in main thread.
     */
    public static void getBitmap(final ImageKey key, final LoadListener<Bitmap> l, final boolean isCrop) {
        Bitmap bitmap = getFromCache(key);
        if (bitmap != null && !bitmap.isRecycled())
            callbackBitmap(bitmap, l);
        else
            getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    callbackBitmap(getBitmapSync(key, isCrop), l);
                }
            });
    }

    /**
     * {@link #getBitmapFromLocal(Uri, LoadListener)}
     */
    public static void getBitmapFromLocal(String uri, LoadListener<Bitmap> l) {
        getBitmapFromLocal(parseURI(uri), l);
    }

    /**
     * Get a bitmap from the memory cache or disk cache.
     *
     * @param uri is the image key.
     * @param l   will callback when the bitmap load in main thread.
     */
    public static void getBitmapFromLocal(final Uri uri, final LoadListener<Bitmap> l) {
        Bitmap bitmap = getFromCache(ImageKey.get(uri, SIZE_ORIGINAL, SIZE_ORIGINAL));
        if (bitmap != null && !bitmap.isRecycled())
            callbackBitmap(bitmap, l);
        else
            getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    callbackBitmap(getBitmapFromLocalSync(uri), l);
                }
            });
    }

    /**
     * {@link #getBitmapSync(Uri)}
     */
    public static Bitmap getBitmapSync(String uri) {
        return getBitmapSync(parseURI(uri));
    }

    /**
     * Get a bitmap with full width and height.
     * This method must not run in the main thread.
     *
     * @param uri is the path of bitmap.
     * @return a bitmap.
     */
    public static Bitmap getBitmapSync(Uri uri) {
        return getBitmapSync(uri, SIZE_ORIGINAL, SIZE_ORIGINAL);
    }

    public static Bitmap getBitmapSync(String uri, int maxSize) {
        return getBitmapSync(parseURI(uri), maxSize);
    }

    public static Bitmap getBitmapSync(Uri uri, int maxSize) {
        return getBitmapSync(ImageKey.get(uri, maxSize, maxSize), false);
    }

    /**
     * {@link #getBitmapSync(Uri, int, int)}
     */
    public static Bitmap getBitmapSync(String uri, int width, int height) {
        return getBitmapSync(parseURI(uri), width, height);
    }

    /**
     * Get a bitmap from memory cache, disk cache or internet.
     * This method must not run in the main thread.
     *
     * @param uri    is the path of bitmap.
     * @param width  is the size of bitmap you want to show.
     * @param height is the size of bitmap you want to show.
     *               The more detail you set the width and height, the fast bitmap load.
     *               If you want to get the full image, i suggest use {@link #SIZE_ORIGINAL}
     *               or use {@link #getBitmapSync(Uri)}, {@link #getBitmapSync(String)}
     *               In the theory, you can use any integer number below or is zero,
     *               but it is not a prefect choice.
     * @return a bitmap.
     */
    public static Bitmap getBitmapSync(Uri uri, int width, int height) {
        return getBitmapSync(ImageKey.get(uri, width, height), true);
    }

    /**
     * Get a bitmap from memory cache, disk cache or internet.
     * This method must not run in the main thread.
     *
     * @param key is the {@link ImageKey}.
     * @return a bitmap.
     */
    public static Bitmap getBitmapSync(ImageKey key, boolean isCrop) {
        if (key.getUri() == null)
            return null;
        int width = key.getWidth();
        int height = key.getHeight();
        Bitmap bitmap = getFromCache(key);
        Log.d(TAG, "get image : " + key.getUri());
        Log.d(TAG, "image is " + (bitmap == null ? "not " : "") + "in the cache.");
        if (bitmap == null || bitmap.isRecycled())
            try {
                long startTime = System.currentTimeMillis();
                if (isCrop) {
                    bitmap = Glide.with(ContextUtils.getContext())
                            .load(key.getUri())
                            .asBitmap()
                            .centerCrop()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(width, height)
                            .into(width, height).get();
                } else {
                    bitmap = Glide.with(ContextUtils.getContext())
                            .load(key.getUri())
                            .asBitmap()
                            .fitCenter()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(width, height)
                            .into(width, height).get();
                }
                Log.d(TAG, "use " + (System.currentTimeMillis() - startTime) + "ms.");
                if (bitmap != null)
                    saveToCache(key, bitmap);
            } catch (InterruptedException | ExecutionException ignore) {
                ignore.printStackTrace();
            }
        return bitmap;
    }

    /**
     * Get a bitmap from the memory cache or disk cache.
     * This method must not run in the main thread.
     *
     * @param uri is the image key.
     * @return a bitmap.
     */
    public static Bitmap getBitmapFromLocalSync(String uri) {
        return getBitmapFromLocalSync(parseURI(uri));
    }

    /**
     * Get a bitmap from the memory cache or disk cache.
     * This method must not run in the main thread.
     *
     * @param uri is the image key.
     * @return a bitmap.
     */
    public static Bitmap getBitmapFromLocalSync(Uri uri) {
        try {
            if (uri == null)
                return null;
            File file = Glide.with(ContextUtils.getContext())
                    .load(uri)
                    .downloadOnly(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get();
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * {@link #saveToLocal(Uri, String, LoadListener)}
     */
    public static void saveToLocal(String uri, String dirPath, LoadListener<Bitmap> l) {
        saveToLocal(parseURI(uri), dirPath, l);
    }

    /**
     * Get bitmap by uri before save.
     *
     * @param uri     is the path of image.
     * @param dirPath is the save path.
     * @param l       will callback succeed or failed after save.
     */
    public static void saveToLocal(final Uri uri, final String dirPath, final LoadListener<Bitmap> l) {
        getBitmap(uri, new LoadListener<Bitmap>() {
            @Override
            public void onLoad(Bitmap bitmap) {
                saveToLocal(uri, dirPath, l);
            }

            @Override
            public void onFailed(int code) {
                if (l != null) {
                    l.onFailed(code);
                }
            }
        });
    }

    /**
     * Save the photo into the public disk on the phone.
     *
     * @param bitmap  is which you want to save.
     * @param dirPath is the save path.
     * @param l       will callback succeed or failed after save.
     */
    public static void saveToLocal(final Bitmap bitmap, final String dirPath, final LoadListener<Bitmap> l) {
        getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean modify = saveToLocalSync(bitmap, dirPath);
                if (modify) {
                    callbackSave(bitmap, l);
                } else {
                    callbackFailed(0, l);
                }
            }
        });
    }

    /**
     * Save the photo into the public disk on the phone.
     *
     * @param bitmap  is which you want to save.
     * @param dirPath is the save path.
     * @param l       will callback succeed or failed after save.
     */
    public static void saveToLocal(final Bitmap bitmap, final String dirPath, final String fileName, final boolean isNotifyAlbum, final LoadListener<Bitmap> l) {
        getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                boolean modify = saveToLocalSync(bitmap, fileName, dirPath, isNotifyAlbum);
                if (modify) {
                    callbackSave(bitmap, l);
                } else {
                    callbackFailed(0, l);
                }
            }
        });
    }

    /**
     * Ensure the callback l will run in a main thread.
     */
    private static void callbackBitmap(final Bitmap bitmap, final LoadListener<Bitmap> l) {
        if (l == null)
            return;
        HandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                l.onLoad(bitmap);
            }
        });
    }

    /**
     * Ensure the callback l will run in a main thread.
     */
    private static void callbackSave(final Bitmap bitmap, final LoadListener<Bitmap> l) {
        if (l == null) {
            return;
        }
        HandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                l.onLoad(bitmap);
            }
        });
    }

    /**
     * Ensure the callback l will run in a main thread.
     */
    private static void callbackFailed(final int code, final LoadListener<Bitmap> l) {
        if (l == null) {
            return;
        }
        HandlerUtils.post(new Runnable() {
            @Override
            public void run() {
                l.onFailed(0);
            }
        });
    }

    /**
     * {@link #saveToLocalSync(Uri, String)}
     */
    public static Bitmap saveToLocalSync(String uri, String dirPath) {
        return saveToLocalSync(parseURI(uri), dirPath);
    }

    /**
     * Get bitmap by uri before save.
     * This method must not run in the main thread.
     *
     * @param uri     is the path of image.
     * @param dirPath is the save path.
     * @return a bitmap which is saved.
     */
    public static Bitmap saveToLocalSync(Uri uri, String dirPath) {
        Bitmap bitmap = getBitmapSync(uri);
        if (saveToLocalSync(bitmap, dirPath))
            return bitmap;
        return null;
    }

    /**
     * Save the photo into the public disk on the phone.
     * This method must not run in the main thread.
     *
     * @param bitmap  is which you want to save.
     * @param dirPath is the save path.
     */
    public static boolean saveToLocalSync(Bitmap bitmap, String dirPath) {
        if (bitmap == null)
            return false;
        boolean isSucceed = false;
        FileOutputStream fos = null;
        try {
            // get file dir and auto create when it is not exists.
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
            String fileName = System.currentTimeMillis() + ".jpg";
            File photo = new File(dir, fileName);
            fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            // input the photo to the system Photos
            MediaStore.Images.Media.insertImage(ContextUtils.getContext().getContentResolver(), photo.getAbsolutePath(), fileName, null);
            ContextUtils.getContext().sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    parseURI(photo.getAbsolutePath())
            ));
            isSucceed = true;
        } catch (IOException ignore) {
            ignore.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
        return isSucceed;
    }

    public static boolean saveToLocalSync(Bitmap bitmap, String fileName, String dirPath) {
        if (bitmap == null)
            return false;
        boolean isSucceed = false;
        FileOutputStream fos = null;
        try {
            // get file dir and auto create when it is not exists.
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
            File photo = new File(dir, fileName);
            fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            isSucceed = true;
        } catch (IOException ignore) {
            ignore.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
        return isSucceed;
    }

    public static boolean saveToLocalSync(Bitmap bitmap, String fileName, String dirPath, boolean isNotifyAlbum) {
        if (bitmap == null)
            return false;
        boolean isSucceed = false;
        FileOutputStream fos = null;
        try {
            // get file dir and auto create when it is not exists.
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
            File photo = new File(dir, fileName);
            fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            isSucceed = true;
            Log.d(TAG, "save image " + fileName + " to " + photo.getAbsolutePath() + "succeed");
            if (isNotifyAlbum) {
                // input the photo to the system Photos
                MediaStore.Images.Media.insertImage(ContextUtils.getContext().getContentResolver(), photo.getAbsolutePath(), fileName, null);
                ContextUtils.getContext().sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        parseURI(photo.getAbsolutePath())
                ));
            }
        } catch (IOException ignore) {
            ignore.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ignore) {
                ignore.printStackTrace();
            }
        }
        return isSucceed;
    }
}
