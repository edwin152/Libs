package com.edwin.androidlib;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


import android.content.Context;

import com.edwin.androidlib.http.Http;
import com.edwin.androidlib.image.Image;
import com.edwin.androidlib.utils.ContextUtils;
import com.edwin.androidlib.utils.HandlerUtils;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public class AndroidLib {

    public static void initialize(Context context) {
        ContextUtils.initialize(context);
        HandlerUtils.initialize();
        Image.initialize();
        Http.initialize(context);
    }
}
