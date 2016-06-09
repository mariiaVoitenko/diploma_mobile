package com.voitenko.diploma.mobile.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Country implements Comparable<Country>{

    @Expose
    private Integer id;

    @Expose
    private String name;

    @Expose
    private Language language;

    public Country() {

    }

    public Country(Integer id, String name, Language language) {
        this.id = id;
        this.name = name;
        this.language = language;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public int compareTo(Country another) {
        return this.getName().compareTo(another.getName());
    }

}
