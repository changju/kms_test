package com.example.demo.aws.kms.service;

import com.example.demo.aws.kms.IKms;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

@Profile("test")
@Service
@Slf4j
public class KmsServiceImpl implements IKms {

    private String AES = "AES";
    @Autowired
    private KmsClient testKmsClient;

    @Override
    public List<Object> encode(String keyId, String data) throws Exception{

        GenerateDataKeyRequest generateDataKeyRequest = GenerateDataKeyRequest.builder()
                .keyId(keyId)
                .keySpec(DataKeySpec.AES_256)
                .build();

        GenerateDataKeyResponse response = testKmsClient.generateDataKey(generateDataKeyRequest);

        System.out.println(String.format("ciphertextBlob: %s", response.ciphertextBlob().toString()));
        System.out.println(String.format("plaintext: %s", response.plaintext().toString()));

        SdkBytes plainTextKey = response.plaintext();
        SdkBytes encryptedKey = response.ciphertextBlob();

        byte[] dataKey = plainTextKey.asByteArray();
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(dataKey, AES));
        byte[] encryptedText = cipher.doFinal(data.getBytes());

        String cipherText = Base64.getEncoder().encodeToString(encryptedText);
        List list = new ArrayList();

        list.add(encryptedKey);
        list.add(cipherText);

        return list;


    }

    @Override
    public Optional<String> decode(SdkBytes encryptedKey, String encValue) throws Exception{
        DecryptRequest decryptRequest = DecryptRequest.builder().ciphertextBlob(encryptedKey).build();//.keyId(keyId).build();
        DecryptResponse decryptResponse = testKmsClient.decrypt(decryptRequest);
        SdkBytes plainText = decryptResponse.plaintext();

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(plainText.asByteArray(), AES));
        byte[] decodeBytes = Base64.getDecoder().decode(encValue);
        byte[] decrypted = cipher.doFinal(decodeBytes);
        String decodeStr = new String(decrypted, "UTF-8");
        System.out.println(String.format("decodeStr: %s".format(decodeStr)));
        return Optional.of(decodeStr);
    }
}