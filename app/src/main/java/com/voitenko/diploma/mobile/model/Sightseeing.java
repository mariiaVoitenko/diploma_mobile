package com.voitenko.diploma.mobile.model;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Sightseeing implements Comparable<Sightseeing>{

    @Expose
    private Integer id;

    @Expose
    private String name;

    @Expose
    private Category category;

    @Expose
    private String info;

    @Expose
    private Float latitude;

    @Expose
    private Float longitude;

    @Expose
    private String photo;

    @Expose
    private Float rating;

    @Expose
    private Long votes_count;

    @Expose
    private Region region;

    public Sightseeing() {

    }

    public Sightseeing(Integer id, String name, Category category, String info, Float latitude,
                       Float longitude, String photo, Float rating, Long votes_count, Region region) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.info = info;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
        this.rating = rating;
        this.region = region;
        this.votes_count = votes_count;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Long getVotes_count() {
        return votes_count;
    }

    public void setVotes_count(Long votes_count) {
        this.votes_count = votes_count;
    }

    @Override
    public int compareTo(Sightseeing another) {
        return this.name.compareTo(another.getName());
    }
}
