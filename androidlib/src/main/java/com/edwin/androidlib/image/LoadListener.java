package com.edwin.androidlib.image;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public interface LoadListener<E> {

    void onLoad(E element);

    void onFailed(int code);
}
