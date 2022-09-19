package com.example.demo.aws.kms;

import software.amazon.awssdk.core.SdkBytes;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IKms {

    List<Object> encode(String key, String data) throws Exception;

    Optional<String> decode(SdkBytes encryptedKey, String encValue) throws Exception;

}