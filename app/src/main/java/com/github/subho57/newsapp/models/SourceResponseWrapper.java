package com.github.subho57.newsapp.models;

import java.util.ArrayList;

public class SourceResponseWrapper {
    private final String status;
    private final ArrayList<Source> sources;

    /**
     * @param status  If the request was successful or not. Options: ok, error.
     * @param sources The results of the request.
     */
    public SourceResponseWrapper(String status, ArrayList<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }
}
