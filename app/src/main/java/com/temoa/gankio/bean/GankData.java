package com.temoa.gankio.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Temoa
 * on 2016/8/2 11:43
 */
public class GankData implements Serializable {
    private static final long serialVersionUID = -201211611214L;

    private boolean error;
    private ArrayList<Result> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public static class Result implements Serializable {
        private static final long serialVersionUID = -201211611222L;

        private String desc;
        private String publishedAt;
        private String type;
        private String url;
        private String who;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
