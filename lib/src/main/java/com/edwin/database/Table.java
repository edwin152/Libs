package com.edwin.database;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/12/22	| xuxiangyu 	| 	create the file
 */


import com.edwin.Cache;

import java.util.List;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/22
 */
public interface Table<K, V> extends Cache {

    long insert(V value);

    boolean insertInTx(List<V> list);

    long insertOrReplace(V value);

    boolean insertOrReplaceInTx(List<V> list);

    long delete(V value);

    long deleteByKey(K key);

    boolean deleteAll();

    long update(V value);

    boolean updateInTx(List<V> list);

    V queryByKey(K key);

    List<V> queryAll();
}
