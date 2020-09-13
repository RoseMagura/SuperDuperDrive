package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.File;
import com.cloudstorage.mapper.FileMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

     public int createFile(File file) {
        return  fileMapper.createFile(file);
    }

     public File getFile(String fileId){ return fileMapper.getFile(fileId);}

     public List<File> getAll() { return fileMapper.getAll();}

     public void editFile(File file) { fileMapper.updateFile(file); }

     public void deleteFile(String fileId) {fileMapper.deleteFile(fileId);}

    public boolean isFilenameAvailable(String filename) {
        return fileMapper.getFileByName(filename) == null;
    }

}
