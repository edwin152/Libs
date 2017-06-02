package com.edwin.androidlib.http;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/6/2	| xuxiangyu 	| 	create the file
 */


/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/6/2
 */
public interface RequestListener<P> {
    void onStart();

    void onSucceed(int code, String msg, P parser);

    void onFailed(int code);

    void onComplete();
}
