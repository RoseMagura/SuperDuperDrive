package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.Credential;
import com.cloudstorage.mapper.CredentialMapper;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAll(){return credentialMapper.getAll();}

     public int createCredential(Credential credential) { return credentialMapper.createCredential(credential);}

    public List<Credential> getForUser(Integer userId) { return credentialMapper.getAllByUser(userId);}

     public Credential getCredential(String credentialId){ return credentialMapper.getCredential(credentialId);}

     public void editCredential(Credential credential) {credentialMapper.updateCredential(credential);}

     public void deleteCredential(String credentialId) {credentialMapper.deleteCredential(credentialId);}
}
