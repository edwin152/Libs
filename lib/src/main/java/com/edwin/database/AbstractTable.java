package com.edwin.database;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/5/22	| xuxiangyu 	| 	create the file
 */


import java.util.concurrent.Executor;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/5/22
 */
public abstract class AbstractTable<K, V> implements Table<K, V> {

    private Executor threadPool;

    public AbstractTable(Executor threadPool) {
        this.threadPool = threadPool;
    }

    public Executor getThreadPool() {
        return threadPool;
    }
}
