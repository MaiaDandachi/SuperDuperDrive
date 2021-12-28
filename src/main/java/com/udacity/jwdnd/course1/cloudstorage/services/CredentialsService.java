package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credentials> getCredentials(int userid){
        return credentialsMapper.getCredentials(userid);
    }

    public void addCredentials(Credentials credentials, int userId){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);

        Credentials newCredentials = new Credentials();
        newCredentials.setUrl(credentials.getUrl());
        newCredentials.setUsername(credentials.getUsername());
        newCredentials.setKey(encodedKey);
        newCredentials.setPassword(encryptedPassword);
        newCredentials.setUserid(userId);

        credentialsMapper.insertCredentilas(newCredentials);
    }

    public int deleteCredentials(int credentialid){
        return credentialsMapper.deleteCredentilas(credentialid);
    }

    public void editCredentials(Credentials credentials){
        Credentials storedCredential = credentialsMapper.getCredentialById(credentials.getCredentialid());

        credentials.setKey(storedCredential.getKey());
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), credentials.getKey());
        credentials.setPassword(encryptedPassword);
        credentialsMapper.updateCredentilas(credentials);
    }
}
