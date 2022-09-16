package com.example.demo.aws.kms.service;

import com.example.demo.aws.kms.IKms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DataKeySpec;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyRequest;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyResponse;

import java.util.Optional;


@Profile("test")
@Service
// https://docs.aws.amazon.com/ko_kr/encryption-sdk/latest/developer-guide/introduction.html

@Slf4j
public class KmsServiceImpl implements IKms {
    @Autowired
    private KmsClient testKmsClient;

    @Override
    public Optional<String> encode(String keyId) {
        //String key_id = "alias/demo_cmk";
        GenerateDataKeyRequest generateDataKeyRequest = GenerateDataKeyRequest.builder()
                .keyId(keyId)
                .keySpec(DataKeySpec.AES_256)
                .build();

        GenerateDataKeyResponse response = testKmsClient.generateDataKey(generateDataKeyRequest);
        log.debug("ciphertextBlob: {}", response.ciphertextBlob().toString());
        log.debug("plaintext: {}", response.plaintext().toString());

        response.plaintext();

        return Optional.empty();
    }


    @Override
    public Optional<Boolean> decode(String encValue) {
        return Optional.empty();
    }
}
