package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * author: Heng Yu
 */
@Service
public class FileService {
    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public String storeFile(MultipartFile multipartFile, String loginUsername) {
        File file = new File();
        Integer userId = userService.getLoginUserId(loginUsername);
        String fileName = FilenameUtils.getName(multipartFile.getOriginalFilename());

        // check if the filename is duplicate, if duplicated throw exception
        this.validationFileName(fileName, userId);

        String type = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String size = CommonUtil.convertFileSize(multipartFile.getSize());

        file.setFileName(fileName);
        file.setFileSize(size);
        file.setUserId(userId);
        file.setContentType(type);

        // get the content of the file
        try {
//            file.setFileData(multipartFile.getInputStream());
            file.setFileData(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileException("Can not save file");
        }

        Integer result = fileMapper.insertFile(file);
        if (result == null) {
            throw new FileException("Save file to DB failed");
        }

        return fileName;
    }


    public File loadFile(Integer fileId, Integer userId) {
        File result = fileMapper.findFileById(fileId, userId).orElseThrow(()->new FileException("No such file"));
        return result;
    }

    /**
     * Get all the files belongs to the user
     * @param loginUserId
     * @return
     */
    public List<File> getFiles(Integer loginUserId) {
        return fileMapper.findAllByUserId(loginUserId);
    }

    /**
     * Delete the file
     * @param fileId
     * @param loginUserId
     * @return
     */
    public String deleteFile(Integer fileId, Integer loginUserId) {
        File fileNeed2Remove = fileMapper.findFileById(fileId, loginUserId).orElseThrow(() -> new FileException("No such file"));

        if (fileMapper.deleteFileById(fileId, loginUserId) == null) {
            throw new FileException("Can not delete the file");
        }
        return fileNeed2Remove.getFileName();
    }

    /**
     * check if the filename is duplicated, if has the same name, throw exception
     *
     * @param newFileName
     * @param userId
     */
    private void validationFileName(String newFileName, Integer userId) {
        fileMapper.findAllByUserId(userId).parallelStream()
                .filter(f -> f.getFileName().equals(newFileName))
                .findAny()
                .ifPresent(f -> {
                    throw new FileException("File " + f.getFileName() + " already exists!");
                });

    }
}
