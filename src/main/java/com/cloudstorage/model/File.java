package com.cloudstorage.model;

import java.util.Arrays;

public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private Integer fileSize;
    private Integer userId;
    private byte[] fileData;


    public File(Integer fileId, String filename, String contentType, Integer fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;

    }

    public Integer getFileId() {
        return fileId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", filename='" + filename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", userId=" + userId +
                ", fileData=" + Arrays.toString(fileData) +
                '}';
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

}
