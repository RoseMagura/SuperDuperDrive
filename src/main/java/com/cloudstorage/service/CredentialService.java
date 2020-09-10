package com.cloudstorage.service;

import org.springframework.stereotype.Service;
import com.cloudstorage.model.Credential;
import com.cloudstorage.mapper.CredentialMapper;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    // public int createCredential(Credential credential) { return credentialMapper.insert(credential);}

//    // password displayed as encrypted and then option to show?

    // public Credential getCredential(String credentialId){ return credentialMapper.getCredential(credentialId);}

    // public Credential editCredential() {}

    // public void deleteCredential(String credentialId) {userMapper.deleteCredential(credentialId);}
}
