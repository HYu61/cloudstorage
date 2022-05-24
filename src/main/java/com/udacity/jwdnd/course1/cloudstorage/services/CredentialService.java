package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.AppException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
@Service
public class CredentialService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserService userService, EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(Credential newCredential) {
        // check if the url and the username combined is duplicate
        validateNewCredential(newCredential);

        // generate the key for the password;
        String key = CommonUtil.generateSecureString();
        newCredential.setKey(key);

        // encrypt the password;
        String plaintPass = newCredential.getPassword();
        newCredential.setPassword(encryptionService.encryptValue(plaintPass, key));

        return credentialMapper.insertCredential(newCredential);

    }

    public List<Credential> getAllCredentials(Integer userId){
        return credentialMapper.findAllByUserId(userId);
    }

    // check if the new credential duplicate for the url and username combined. if duplicate throw exception
    private void validateNewCredential(Credential newCredential){
        List<Credential> credentialList = credentialMapper.findAllByUserId(newCredential.getUserId());

        Optional<Credential> duplicateCredential = credentialList.parallelStream().
                filter(c -> c.getUrl().equals(newCredential.getUrl())
                        && c.getUsername().equals(newCredential.getUsername()))
                .findAny();

        if (duplicateCredential.isPresent()) {
            throw new AppException(String.format("URL: %s WITH USERNAME: %s ALREADY EXISTS!",
                    newCredential.getUrl(), newCredential.getUsername()));
        }
    }
}
