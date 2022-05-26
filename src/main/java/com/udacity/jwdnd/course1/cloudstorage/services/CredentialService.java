package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.CredentialException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
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

    /**
     * Insert the credential into DB
     *
     * @param credentialDto
     * @param loginUsername
     * @return the credentialID of the inserted credential
     */
    public Integer addCredential(CredentialDto credentialDto, String loginUsername) {

        Credential newCredential = new Credential();
        BeanUtils.copyProperties(credentialDto, newCredential);

        // set userId to the credential model, if the user is not exists, throw exception.
        Integer loginUserId = userService.getLoginUserId(loginUsername);

        newCredential.setUserId(loginUserId);

        // check if the url and the username combined is duplicate, if duplicate throw exception
        validateNewCredential(newCredential, loginUserId);

        // encrypt the password;
        String key = CommonUtil.generateSecureString();
        newCredential.setKey(key);
        String encryptPassword = encryptionService.encryptValue(credentialDto.getPassword(), key);
        newCredential.setPassword(encryptPassword);

        Integer result = credentialMapper.insertCredential(newCredential);
        if (result == null){
            throw new CredentialException("Can not add the credential");
        }
        return result;

    }

    /**
     * Get all the credentials from the owner user
     *
     * @param userId the login user's id
     * @return the list of the credentials that the user created.
     */
    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.findAllByUserId(userId);
    }


    /**
     * Modified credential.
     *
     * @param credentialDto
     * @param loginUsername
     * @return
     */
    public Integer modifyCredential(CredentialDto credentialDto, String loginUsername) {


        // make sure the edited credential is belongs to the login user
        Integer loginUserId = userService.getLoginUserId(loginUsername);

        Credential newCredential = credentialMapper.findCredentialById(credentialDto.getCredentialId(), loginUserId)
                .orElseThrow(() -> new CredentialException("Can not find the credential!"));

        // encrypt the modified password
        String newPassword = encryptionService.encryptValue(credentialDto.getPassword(), newCredential.getKey());
        newCredential.setPassword(newPassword);

        Integer result = credentialMapper.updateCredentialById(newCredential, loginUserId);
        if(result == null){
            throw new CredentialException("Can not modify the credential");
        }

        return result;
    }

    /**
     * @param credentialId
     * @param loginUsername
     * @return the removed credential
     */
    public Credential removeCredential(Integer credentialId, String loginUsername) {
        Integer loginUserId = userService.getLoginUserId(loginUsername);

        Credential need2Removed = credentialMapper.findCredentialById(credentialId, loginUserId).orElseThrow(() -> new CredentialException("No such credential"));

        Integer result = credentialMapper.deleteCredentialById(credentialId, loginUserId);
        if (result == null) {
            throw new CredentialException("Delete credential failed");
        }
        return need2Removed;
    }

    public String decryptionPassword(Credential credential, CredentialDto credentialDto) {
        String key = credential.getKey();
        BeanUtils.copyProperties(credential, credentialDto);
        String result = encryptionService.decryptValue(credential.getPassword(), key);
        credentialDto.setPlaintPassword(result);
        return result;
    }

    /**
     * Check if the new credential duplicate for the url and username combined. If duplicate throw exception
     *
     * @param newCredential
     */
    private void validateNewCredential(Credential newCredential, Integer userId) {
        List<Credential> credentialList = credentialMapper.findAllByUserId(userId);

        // check if the new credential has the same url and username in the list.
        Optional<Credential> duplicateCredential = credentialList.parallelStream().
                filter(c -> c.getUrl().equals(newCredential.getUrl())
                        && c.getUsername().equals(newCredential.getUsername()))
                .findAny();

        if (duplicateCredential.isPresent()) {
            throw new CredentialException(String.format("url: %s with username: %s already exists!",
                    newCredential.getUrl(), newCredential.getUsername()));
        }
    }
}
