package com.edwin.androidlib.utils;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


import android.os.Handler;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public class HandlerUtils {

    private static Handler handler;

    public static void initialize() {
        handler = new Handler();
    }

    public static void post(Runnable r) {
        if (handler == null) {
            throw new NullPointerException("please initialize in application.");
        }
        handler.post(r);
    }

    public static void postDelayed(Runnable r, long delayMillis) {
        if (handler == null) {
            throw new NullPointerException("please initialize in application.");
        }
        handler.postDelayed(r, delayMillis);
    }

}
