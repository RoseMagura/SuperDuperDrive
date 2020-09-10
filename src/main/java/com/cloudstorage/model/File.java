package com.cloudstorage.model;

public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private Integer userId;

    public Integer getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getUserId() {
        return userId;
    }

}
