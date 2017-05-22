package com.edwin.concurrent;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public class ThreadPoolFactory {

    public static Executor newTreadPool() {
        return Executors.newCachedThreadPool();
    }

    public static Executor newSingleTreadPool() {
        return Executors.newSingleThreadExecutor();
    }

    public static Executor newFixThreadPool(int i) {
        if (i <= 0) {
            throw new RuntimeException("i <= 0");
        }
        return Executors.newFixedThreadPool(i);
    }

}
