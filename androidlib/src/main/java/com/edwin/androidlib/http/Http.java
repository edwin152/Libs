package com.edwin.androidlib.http;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/6/2	| xuxiangyu 	| 	create the file
 */


import android.content.Context;

import com.edwin.Manager;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/6/2
 */
public class Http implements Manager {

    private static RequestQueue requestQueue;
    private static DownloadQueue downloadQueue;

    public static void initialize(Context context) {
        NoHttp.initialize(context);
        requestQueue = NoHttp.newRequestQueue(1);
        downloadQueue = NoHttp.newDownloadQueue(1);
    }

    // TODO: 2017/6/2 not complete
    public static <P> void request(final RequestModel<P> requestModel) {
        if (requestQueue == null) {
            throw new NullPointerException("please initialize in application.");
        }
        final Request<String> request = NoHttp.createStringRequest(requestModel.getUrl());
        request.add(requestModel.getMap());
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                if (requestModel.getRequestListener() != null) {
                    requestModel.getRequestListener().onStart();
                }
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                if (requestModel.getRequestListener() != null) {
                    requestModel.getRequestListener().onSucceed(0, "", null);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (requestModel.getRequestListener() != null) {
                    requestModel.getRequestListener().onFailed(0);
                }
            }

            @Override
            public void onFinish(int what) {
                if (requestModel.getRequestListener() != null) {
                    requestModel.getRequestListener().onComplete();
                }
            }
        });
    }

    // TODO: 2017/6/2 not complete
    public static <P> void download(final RequestModel<P> requestModel) {
        downloadQueue.add(0, null, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {

            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {

            }

            @Override
            public void onFinish(int what, String filePath) {

            }

            @Override
            public void onCancel(int what) {

            }
        });
    }
}
