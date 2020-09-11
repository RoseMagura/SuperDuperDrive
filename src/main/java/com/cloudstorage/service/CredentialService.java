package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.Credential;
import com.cloudstorage.mapper.CredentialMapper;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAll(){return credentialMapper.getAll();}

     public int createCredential(Credential credential) { return credentialMapper.createCredential(credential);}

    // password displayed as encrypted and then option to show?

     public Credential getCredential(String credentialId){ return credentialMapper.getCredential(credentialId);}

     public void editCredential(Credential credential) {credentialMapper.updateCredential(credential);}

     public void deleteCredential(String credentialId) {credentialMapper.deleteCredential(credentialId);}
}
