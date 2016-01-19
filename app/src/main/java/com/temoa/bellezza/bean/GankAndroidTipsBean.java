package com.temoa.bellezza.bean;

import java.util.List;

public class GankAndroidTipsBean {

    /**
     * who : MVP
     * publishedAt : 2016-01-19T04:06:04.449Z
     * desc : RxVolley，支持RxJava，OKhttp，内置了一个RxBus，移除了httpclient相关API
     * type : Android
     * url : https://github.com/kymjs/RxVolley
     * used : true
     * objectId : 569da6f8c24aa80053ae03a2
     * createdAt : 2016-01-19T03:01:12.114Z
     * updatedAt : 2016-01-19T04:06:05.298Z
     */

    private List<ResultsEntity> results;

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private String desc;
        private String url;

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public String getUrl() {
            return url;
        }
    }
}
