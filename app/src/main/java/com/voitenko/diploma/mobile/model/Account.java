package com.voitenko.diploma.mobile.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

import java.util.List;

@Generated("org.jsonschema2pojo")
public class Account {

    @Expose
    private Integer id;

    @Expose
    private String login;

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String email;

    @Expose
    private boolean activated;

    @Expose
    private String langKey;

    @Expose
    private List<String> authorities;

    public Account() {
    }

    public Account(String firstName, Integer id, String login, String lastName, String email,
                   boolean activated, String langKey, List<String> authorities) {
        this.firstName = firstName;
        this.id = id;
        this.login = login;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
