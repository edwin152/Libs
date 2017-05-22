package com.edwin.androidlib.utils;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


import android.annotation.SuppressLint;
import android.content.Context;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public class ContextUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void initialize(Context context) {
        ContextUtils.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
