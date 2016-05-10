package com.voitenko.diploma.mobile.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Code {

    @Expose
    private Integer id;

    @Expose
    private String picture;

    @Expose
    private Sightseeing sightseeing;

    public Code() {
    }

    public Code(Integer id, String picture, Sightseeing sightseeing) {
        this.id = id;
        this.picture = picture;
        this.sightseeing = sightseeing;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Sightseeing getSightseeing() {
        return sightseeing;
    }

    public void setSightseeing(Sightseeing sightseeing) {
        this.sightseeing = sightseeing;
    }
}
