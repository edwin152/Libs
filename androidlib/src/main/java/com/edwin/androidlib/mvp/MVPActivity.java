package com.edwin.androidlib.mvp;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/6/2	| xuxiangyu 	| 	create the file
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/6/2
 */
public abstract class MVPActivity<P extends MVPPresenter> extends AppCompatActivity {

    private P presenter;

    public MVPActivity() {
        this.presenter = newPresenter();
    }

    protected abstract P newPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    protected P getPresenter() {
        return presenter;
    }
}
