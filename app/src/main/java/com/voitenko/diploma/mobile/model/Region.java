package com.voitenko.diploma.mobile.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Region implements Comparable<Region>{

    @Expose
    private Integer id;

    @Expose
    private String name;

    @Expose
    private Country country;

    public Region() {

    }

    public Region(String name) {
        this.name = name;
    }

    public Region(int id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public int compareTo(Region another) {
        return this.getName().compareTo(another.getName());
    }
}
