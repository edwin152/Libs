package com.edwin.androidlib.http;

/**
 * Copyright 2016 Beijing Xinwei, Inc. All rights reserved.
 * <p>
 * History:
 * ------------------------------------------------------------------------------
 * Date    	    |  Who  		|  What
 * 2017/6/2	| xuxiangyu 	| 	create the file
 */


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * simple description
 * detail description
 *
 * @author xuxiangyu create on 2017/6/2
 */
public class RequestModel<P> {
    private String url;
    private Method method;
    private Map<String, String> map;
    private Map<String, String> header;
    private String fileKey;
    private File uploadFile;
    private File certify;
    private RequestListener<P> requestListener;
    private FileLoadListener<P> fileLoadListener;

    public RequestModel(String url) {
        this(url, Method.GET);
    }

    public RequestModel(String url, Method method) {
        this.url = url;
        this.method = method;
        this.map = new HashMap<>();
        this.header = new HashMap<>();
    }

    public void add(String key, String value) {
        this.map.put(key, value);
    }

    public void addHeader(String key, String value) {
        this.header.put(key, value);
    }

    public void setFile(String key, File file) {
        this.fileKey = key;
        this.uploadFile = file;
    }

    public void setCertify(File file) {
        this.certify = file;
    }

    public void setRequestListener(RequestListener<P> l) {
        this.requestListener = l;
    }

    public void setFileLoadListener(FileLoadListener<P> l) {
        this.fileLoadListener = l;
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public String getFileKey() {
        return fileKey;
    }

    public File getUploadFile() {
        return uploadFile;
    }

    public File getCertify() {
        return certify;
    }

    public RequestListener<P> getRequestListener() {
        return requestListener;
    }

    public FileLoadListener getFileLoadListener() {
        return fileLoadListener;
    }

    public static class Builder {
        private RequestModel requestModel;

        public Builder(String url) {
            requestModel = new RequestModel(url);
        }

        public Builder(String url, Method method) {
            requestModel = new RequestModel(url, method);
        }

        public Builder add(String key, String value) {
            requestModel.add(key, value);
            return this;
        }

        public Builder addHeader(String key, String value) {
            requestModel.addHeader(key, value);
            return this;
        }

        public Builder setFile(String key, File file) {
            requestModel.setFile(key, file);
            return this;
        }

        public Builder setCertify(File file) {
            requestModel.setCertify(file);
            return this;
        }

        public Builder setRequestListener(RequestListener l) {
            requestModel.setRequestListener(l);
            return this;
        }

        public Builder setFileLoadListener(FileLoadListener l) {
            requestModel.setFileLoadListener(l);
            return this;
        }

        public RequestModel build() {
            return requestModel;
        }
    }
}
