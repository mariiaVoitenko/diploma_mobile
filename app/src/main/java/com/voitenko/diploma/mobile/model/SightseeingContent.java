package com.voitenko.diploma.mobile.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SightseeingContent {

    @Expose
    private Integer id;

    @Expose
    private Content content;

    @Expose
    private Sightseeing sightseeing;

    public SightseeingContent() {
    }

    public SightseeingContent(Integer id, Content content, Sightseeing sightseeing) {
        this.id = id;
        this.content = content;
        this.sightseeing = sightseeing;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Sightseeing getSightseeing() {
        return sightseeing;
    }

    public void setSightseeing(Sightseeing sightseeing) {
        this.sightseeing = sightseeing;
    }

}
