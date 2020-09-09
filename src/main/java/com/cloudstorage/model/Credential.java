package com.cloudstorage.model;

public class Credential {
    private Integer credentialId;
    private String url;
    private String username;
    private String password; //encrypted?
    private Integer userId;

    public Credential(Integer credentialId, String url, String username, String password, Integer userId){
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialId() { return credentialId;}

    // should there be a method to set the credential id? Maybe not

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUserId() {
        return userId;
    }

    // maybe users shouldn't be able to change the user
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
