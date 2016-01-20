package com.temoa.bellezza.bean;

import java.util.List;

public class GankAndroidTipsBean {

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
