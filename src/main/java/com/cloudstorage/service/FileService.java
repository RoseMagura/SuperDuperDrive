package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.File;
import com.cloudstorage.mapper.FileMapper;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

     public int createFile(File file) { return fileMapper.createFile(file);}

    // public File getFile(String fileId){ return fileMapper.getFile(fileId);}

    // public File editFile() {}

    // public void deleteFile(String fileId) {userMapper.deleteFile(fileId);}
}
