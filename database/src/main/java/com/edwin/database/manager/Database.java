package com.edwin.database.manager;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/6/2	| xuxiangyu 	| 	create the file
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.edwin.Manager;
import com.edwin.Session;
import com.edwin.database.dao.DaoMaster;
import com.edwin.database.dao.DaoSession;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/6/2
 */
@SuppressLint("StaticFieldLeak")
public class Database implements Manager, Session {

    private static volatile Database instance;
    private static Context appContext;

    private String user;

    public static void initialize(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        appContext = context.getApplicationContext();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
    }

    @Override
    public synchronized boolean login(String user) {
        if (user == null) {
            return false;
        }
        if (user.equals(this.user)) {
            return true;
        }
        this.user = user;
        DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper(appContext, "db2_" + user) {
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                Tables.updateAllTables(db);
            }
        };
        DaoSession daoSession = new DaoMaster(helper.getWritableDb()).newSession();
        Tables.setDaoSession(daoSession);
        return true;
    }

    @Override
    public boolean logout() {
        instance = null;
        Tables.setDaoSession(null);
        return true;
    }

    private static class Tables {

        private static void setDaoSession(DaoSession daoSession) {

        }

        @SuppressWarnings("unchecked")
        private static void updateAllTables(SQLiteDatabase db) {
            MigrationHelper.migrate(db);
        }
    }

}
