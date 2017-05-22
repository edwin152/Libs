package com.edwin.androidlib.database;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2016/12/22	| xuxiangyu 	| 	create the file
 */


import com.edwin.database.AbstractTable;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2016/12/22
 */
public abstract class BaseGreenDaoTable<K, V, D extends AbstractDao<V, K>> extends AbstractTable<K, V> {

    private D dao;

    public BaseGreenDaoTable(Executor threadPool) {
        super(threadPool);
    }

    @Override
    public long insert(V value) {
        return getDao().insert(value);
    }

    @Override
    public boolean insertInTx(List<V> list) {
        try {
            getDao().insertInTx(list);
            return true;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long insertOrReplace(V value) {
        return getDao().insertOrReplace(value);
    }

    @Override
    public boolean insertOrReplaceInTx(List<V> list) {
        try {
            getDao().insertOrReplaceInTx(list);
            return true;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long delete(V value) {
        try {
            getDao().delete(value);
            return 0;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long deleteByKey(K key) {
        try {
            getDao().deleteByKey(key);
            return 0;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            getDao().deleteAll();
            return true;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long update(V value) {
        try {
            getDao().update(value);
            return 0;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean updateInTx(List<V> list) {
        try {
            getDao().updateInTx(list);
            return true;
        } catch (Exception e) {
            // TODO: 2017/5/22 edwin
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public V queryByKey(K key) {
        return getDao().load(key);
    }

    @Override
    public List<V> queryAll() {
        return getDao().loadAll();
    }

    protected QueryBuilder<V> queryBuilder() {
        return getDao().queryBuilder();
    }

    public D getDao() {
        if (dao == null)
            throw new NullPointerException("dao is null, use setDao(D dao) before.");
        return dao;
    }

    public void setDao(D dao) {
        if (dao == null)
            throw new NullPointerException("dao is null");
        this.dao = dao;
    }
}

